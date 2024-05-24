package com.bloggios.user.implementation;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.user.constants.DataErrorCodes;
import com.bloggios.user.constants.InternalErrorCodes;
import com.bloggios.user.constants.ResponseMessageConstants;
import com.bloggios.user.dao.implementation.pgsqlimplementation.ProfileEntityDao;
import com.bloggios.user.document.ProfileDocument;
import com.bloggios.user.enums.DaoStatus;
import com.bloggios.user.exception.payloads.BadRequestException;
import com.bloggios.user.exception.payloads.InternalException;
import com.bloggios.user.feign.AuthProviderApplicationFeign;
import com.bloggios.user.feign.implementation.UserProfileResponseFeignCall;
import com.bloggios.user.modal.ProfileEntity;
import com.bloggios.user.payload.request.ProfileRequest;
import com.bloggios.user.payload.response.ModuleResponse;
import com.bloggios.user.payload.response.UserProfileResponse;
import com.bloggios.user.persistence.ProfileEntityToDocumentPersistence;
import com.bloggios.user.processor.KafkaProcessor.ProfileAddedKafkaProcessor;
import com.bloggios.user.service.ProfileService;
import com.bloggios.user.transformer.implementation.ProfileRequestToProfileEntityTransformer;
import com.bloggios.user.validator.implementation.exhibitor.ProfileRequestExhibitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.implementation
 * Created_on - May 23 - 2024
 * Created_at - 21:30
 */

@Service
public class ProfileServiceImplementation implements ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileServiceImplementation.class);

    private final ProfileRequestExhibitor profileRequestExhibitor;
    private final ProfileEntityDao profileEntityDao;
    private final ProfileRequestToProfileEntityTransformer profileRequestToProfileEntityTransformer;
    private final ProfileEntityToDocumentPersistence profileEntityToDocumentPersistence;
    private final ProfileAddedKafkaProcessor profileAddedKafkaProcessor;

    public ProfileServiceImplementation(
            ProfileRequestExhibitor profileRequestExhibitor,
            ProfileEntityDao profileEntityDao,
            ProfileRequestToProfileEntityTransformer profileRequestToProfileEntityTransformer,
            ProfileEntityToDocumentPersistence profileEntityToDocumentPersistence,
            ProfileAddedKafkaProcessor profileAddedKafkaProcessor
    ) {
        this.profileRequestExhibitor = profileRequestExhibitor;
        this.profileEntityDao = profileEntityDao;
        this.profileRequestToProfileEntityTransformer = profileRequestToProfileEntityTransformer;
        this.profileEntityToDocumentPersistence = profileEntityToDocumentPersistence;
        this.profileAddedKafkaProcessor = profileAddedKafkaProcessor;
    }

    @Override
    public CompletableFuture<ModuleResponse> addProfile(ProfileRequest profileRequest, AuthenticatedUser authenticatedUser, HttpServletRequest httpServletRequest) {
        long startTime = System.currentTimeMillis();
        profileRequestExhibitor.validate(profileRequest);
        Optional<ProfileEntity> profileOptional = profileEntityDao.findByUserId(authenticatedUser.getUserId());
        if (profileOptional.isPresent()) throw new BadRequestException(DataErrorCodes.PROFILE_ALREADY_PRESENT);
        ProfileEntity profileEntityTransformed = profileRequestToProfileEntityTransformer.transform(profileRequest, authenticatedUser);
        ProfileEntity profileEntity = profileEntityDao.initOperation(DaoStatus.CREATE, profileEntityTransformed);
        ProfileDocument profileDocument = profileEntityToDocumentPersistence.persist(profileEntity, DaoStatus.CREATE);
        CompletableFuture.runAsync(()-> profileAddedKafkaProcessor.process(profileDocument));
        logger.info("Execution Time (Add Profile) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(
                ModuleResponse
                        .builder()
                        .message(ResponseMessageConstants.PROFILE_ADDED)
                        .id(profileDocument.getProfileId())
                        .userId(authenticatedUser.getUserId())
                        .build()
        );
    }

    @Override
    public CompletableFuture<ModuleResponse> addImageToProfile(MultipartFile multipartFile, AuthenticatedUser authenticatedUser, String uploadFor) {
        return null;
    }
}
