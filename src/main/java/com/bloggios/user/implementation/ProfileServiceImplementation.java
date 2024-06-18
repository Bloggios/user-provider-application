package com.bloggios.user.implementation;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.elasticsearch.configuration.payload.ListRequest;
import com.bloggios.elasticsearch.configuration.payload.lspayload.Filter;
import com.bloggios.elasticsearch.configuration.payload.lspayload.Sort;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;
import com.bloggios.user.constants.*;
import com.bloggios.user.dao.implementation.esimplementation.ProfileDocumentDao;
import com.bloggios.user.dao.implementation.pgsqlimplementation.ProfileEntityDao;
import com.bloggios.user.document.ProfileDocument;
import com.bloggios.user.enums.DaoStatus;
import com.bloggios.user.enums.ProfileTag;
import com.bloggios.user.exception.payloads.BadRequestException;
import com.bloggios.user.feign.implementation.BlogsCountResponseCallFeign;
import com.bloggios.user.file.DeleteFile;
import com.bloggios.user.file.UploadFile;
import com.bloggios.user.modal.ProfileEntity;
import com.bloggios.user.payload.request.ProfileListRequest;
import com.bloggios.user.payload.request.ProfileRequest;
import com.bloggios.user.payload.response.*;
import com.bloggios.user.persistence.ProfileEntityToDocumentPersistence;
import com.bloggios.user.processor.KafkaProcessor.ProfileAddedKafkaProcessor;
import com.bloggios.user.service.ProfileService;
import com.bloggios.user.transformer.implementation.*;
import com.bloggios.user.utils.LinkGenerator;
import com.bloggios.user.utils.ValueCheckerUtil;
import com.bloggios.user.validator.implementation.exhibitor.ProfileImageExhibitor;
import com.bloggios.user.validator.implementation.exhibitor.ProfileRequestExhibitor;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.*;
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
@RequiredArgsConstructor
public class ProfileServiceImplementation implements ProfileService {

    private static final Logger logger = LoggerFactory.getLogger(ProfileServiceImplementation.class);

    private final ProfileRequestExhibitor profileRequestExhibitor;
    private final ProfileEntityDao profileEntityDao;
    private final ProfileRequestToProfileEntityTransformer profileRequestToProfileEntityTransformer;
    private final ProfileEntityToDocumentPersistence profileEntityToDocumentPersistence;
    private final ProfileAddedKafkaProcessor profileAddedKafkaProcessor;
    private final ProfileImageExhibitor profileImageExhibitor;
    private final Environment environment;
    private final DeleteFile deleteFile;
    private final UploadFile uploadFile;
    private final LinkGenerator linkGenerator;
    private final UpdateProfileRequestTransformer updateProfileRequestTransformer;
    private final ProfileDocumentDao profileDocumentDao;
    private final ProfileDocumentToProfileInternalResponseTransformer profileDocumentToProfileInternalResponseTransformer;
    private final BlogsCountResponseCallFeign blogsCountResponseCallFeign;
    private final ProfileEntityToProfileResponseTransformer profileEntityToProfileResponseTransformer;
    private final ProfileListToListRequestTransformer profileListToListRequestTransformer;
    private final ProfileDocumentToProfileResponseTransformer profileDocumentToProfileResponseTransformer;
    private final ProfileDocumentToUsernameProfileListTransformer profileDocumentToUsernameProfileListTransformer;

    @Override
    @Async(BeanConstants.ASYNC_TASK_EXTERNAL_POOL)
    public CompletableFuture<ModuleResponse> addProfile(ProfileRequest profileRequest, AuthenticatedUser authenticatedUser, HttpServletRequest httpServletRequest) {
        long startTime = System.currentTimeMillis();
        profileRequestExhibitor.validate(profileRequest);
        Optional<ProfileEntity> profileOptional = profileEntityDao.findByUserId(authenticatedUser.getUserId());
        if (profileOptional.isPresent()) throw new BadRequestException(DataErrorCodes.PROFILE_ALREADY_PRESENT);
        ProfileEntity profileEntityTransformed = profileRequestToProfileEntityTransformer.transform(profileRequest, authenticatedUser);
        ProfileEntity profileEntity = profileEntityDao.initOperation(DaoStatus.CREATE, profileEntityTransformed);
        ProfileDocument profileDocument = profileEntityToDocumentPersistence.persist(profileEntity, DaoStatus.CREATE);
        CompletableFuture.runAsync(() -> profileAddedKafkaProcessor.process(profileDocument));
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
    @Async(BeanConstants.ASYNC_TASK_EXTERNAL_POOL)
    public CompletableFuture<ModuleResponse> profileImage(MultipartFile multipartFile, AuthenticatedUser authenticatedUser) {
        long startTime = System.currentTimeMillis();
        profileImageExhibitor.validate(multipartFile);
        ProfileEntity profileEntity = profileEntityDao.findByUserId(authenticatedUser.getUserId())
                .orElseThrow(() -> new BadRequestException(DataErrorCodes.PROFILE_NOT_FOUND));
        String profileImagePath = environment.getProperty(EnvironmentConstants.PROFILE_IMAGE_PATH);
        if (Objects.nonNull(profileEntity.getProfileImage())) {
            CompletableFuture.runAsync(() -> deleteFile.deleteImage(profileImagePath, profileEntity.getProfileImage()));
        }
        CompletableFuture<String> imageNameFuture = CompletableFuture.supplyAsync(() -> uploadFile.uploadImage(profileImagePath, multipartFile, authenticatedUser.getUserId()));
        String link = linkGenerator.generateLink(imageNameFuture.join());
        profileEntity.setProfileImage(link);
        profileEntity.setUpdatedOn(Date.from(Instant.now()));
        profileEntity.setApiVersion(EnvironmentConstants.APPLICATION_VERSION);
        profileEntity.setVersion(UUID.randomUUID().toString());
        ProfileEntity profileEntityResponse = profileEntityDao.initOperation(DaoStatus.UPDATE, profileEntity);
        ProfileDocument profileDocument = profileEntityToDocumentPersistence.persist(profileEntityResponse, DaoStatus.UPDATE);
        logger.info("Execution Time (Profile Image) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(
                ModuleResponse
                        .builder()
                        .message(ResponseMessageConstants.PROFILE_IMAGE_ADDED)
                        .userId(profileDocument.getUserId())
                        .id(profileDocument.getProfileId())
                        .build()
        );
    }

    @Override
    @Async(BeanConstants.ASYNC_TASK_EXTERNAL_POOL)
    public CompletableFuture<ModuleResponse> updateProfile(ProfileRequest profileRequest, AuthenticatedUser authenticatedUser) {
        long startTime = System.currentTimeMillis();
        CompletableFuture<Optional<ProfileEntity>> optionalProfile = CompletableFuture.supplyAsync(() -> profileEntityDao.findByUserId(authenticatedUser.getUserId()));
        CompletableFuture<Void> validateFuture = CompletableFuture.runAsync(() -> profileRequestExhibitor.validate(profileRequest));
        CompletableFuture.allOf(optionalProfile, validateFuture).join();
        Optional<ProfileEntity> profileEntityOptional = optionalProfile.join();
        if (profileEntityOptional.isEmpty())
            throw new BadRequestException(DataErrorCodes.PROFILE_NOT_FOUND);
        ProfileEntity transform = updateProfileRequestTransformer.transform(profileRequest, profileEntityOptional.get());
        ProfileEntity profileEntityResponse = profileEntityDao.initOperation(DaoStatus.UPDATE, transform);
        ProfileDocument profileDocument = profileEntityToDocumentPersistence.persist(profileEntityResponse, DaoStatus.UPDATE);
        logger.info("Execution Time (Update Profile) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(
                ModuleResponse
                        .builder()
                        .message(ResponseMessageConstants.PROFILE_UPDATED)
                        .userId(profileDocument.getUserId())
                        .id(profileDocument.getProfileId())
                        .build()
        );
    }

    @Override
    @Async(BeanConstants.ASYNC_TASK_EXTERNAL_POOL)
    public CompletableFuture<ProfileInternalResponse> getProfileInternalResponse(String userId) {
        long startTime = System.currentTimeMillis();
        ValueCheckerUtil.isValidUUID(userId);
        ProfileDocument profileDocument = profileDocumentDao.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException(DataErrorCodes.PROFILE_NOT_FOUND));
        ProfileInternalResponse transform = profileDocumentToProfileInternalResponseTransformer.transform(profileDocument);
        logger.info("Execution Time (Get Profile Internal Response) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(transform);
    }

    @Override
    @Async(BeanConstants.ASYNC_TASK_EXTERNAL_POOL)
    public CompletableFuture<ProfileResponse> getUserProfile(String username, AuthenticatedUser authenticatedUser) {
        long startTime = System.currentTimeMillis();
        ProfileEntity profileEntity = profileEntityDao.findByUsername(username)
                .orElseThrow(() -> new BadRequestException(DataErrorCodes.PROFILE_NOT_FOUND));
        BlogCountResponse blogCountResponse = blogsCountResponseCallFeign.callFeign(profileEntity.getUserId())
                .orElse(BlogCountResponse.builder().blogs(0).build());
        ProfileResponse transform = profileEntityToProfileResponseTransformer.transform(profileEntity, blogCountResponse);
        if (Objects.isNull(authenticatedUser)) {
            transform.setCreatedOn(null);
            transform.setUpdatedOn(null);
            transform.setVersion(null);
        }
        logger.info("Execution Time (Get User Profile) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(transform);
    }

    @Override
    @Async(BeanConstants.ASYNC_TASK_EXTERNAL_POOL)
    public CompletableFuture<ProfileTagResponse> getProfileTags() {
        long startTime = System.currentTimeMillis();
        List<String> tags = Arrays
                .stream(ProfileTag.values())
                .parallel()
                .map(ProfileTag::getValue)
                .toList();
        logger.info("Execution Time (Get Profile Tags) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(
                ProfileTagResponse
                        .builder()
                        .tags(tags)
                        .totalRecordCounts(tags.size())
                        .build()
        );
    }

    @Override
    @Async(BeanConstants.ASYNC_TASK_EXTERNAL_POOL)
    public CompletableFuture<ListResponse> getProfileList(ProfileListRequest profileListRequest) {
        long startTime = System.currentTimeMillis();
        if (Objects.isNull(profileListRequest)) throw new BadRequestException(DataErrorCodes.USER_LIST_REQUEST_NULL);
        ListRequest transform = profileListToListRequestTransformer.transform(profileListRequest);
        SearchHits<ProfileDocument> searchHits = profileDocumentDao.profileDocumentSearchHits(transform);
        List<ProfileDocument> list = new ArrayList<>();
        if (Objects.nonNull(searchHits)) {
            list = searchHits
                    .stream()
                    .map(SearchHit::getContent)
                    .toList();
        }
        logger.info("Execution Time (Get Profiles List) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(
                ListResponse
                        .builder()
                        .page(profileListRequest.getPage())
                        .size(profileListRequest.getSize())
                        .pageSize(list.size())
                        .totalRecordsCount(searchHits != null ? searchHits.getTotalHits() : 0)
                        .object(list)
                        .build());
    }

    @Override
    @Async(BeanConstants.ASYNC_TASK_EXTERNAL_POOL)
    public CompletableFuture<ProfileResponse> getMyProfile(AuthenticatedUser authenticatedUser) {
        long startTime = System.currentTimeMillis();
        Optional<ProfileDocument> profileOptional = profileDocumentDao.findByUserId(authenticatedUser.getUserId());
        if (profileOptional.isEmpty()) throw new BadRequestException(DataErrorCodes.PROFILE_NOT_FOUND);
        ProfileResponse transform = profileDocumentToProfileResponseTransformer.transform(profileOptional.get());
        logger.info("Execution Time (Get My Profile) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(transform);
    }

    @Override
    public CompletableFuture<ListResponse> fetchProfilesUsingUsername(String username) {
        long startTime = System.currentTimeMillis();
        if (ObjectUtils.isEmpty(username)) throw new BadRequestException(DataErrorCodes.USERNAME_MANDATORY);
        Sort sort = Sort
                .builder()
                .sortKey(ServiceConstants.NAME_KEY)
                .order(SortOrder.ASC)
                .build();
        Filter filter = Filter
                .builder()
                .filterKey(ServiceConstants.USERNAME_KEY)
                .selections(Collections.singletonList(username))
                .build();
        ProfileListRequest profileListRequest = ProfileListRequest
                .builder()
                .page(0)
                .size(10)
                .sorts(Collections.singletonList(sort))
                .filters(Collections.singletonList(filter))
                .build();
        ListRequest transform = profileListToListRequestTransformer.transform(profileListRequest);
        SearchHits<ProfileDocument> searchHits = profileDocumentDao.profileDocumentSearchHits(transform);
        List<ProfileResponse> list = new ArrayList<>();
        if (Objects.nonNull(searchHits)) {
            list = searchHits
                    .stream()
                    .map(SearchHit::getContent)
                    .map(profileDocumentToUsernameProfileListTransformer::transform)
                    .toList();
        }
        List<ProfileResponse> notStartsWithInitialLetter = list
                .stream()
                .filter(profile -> !profile.getName().startsWith(String.valueOf(username.charAt(0))))
                .toList();

        logger.info("Execution Time (Profiles List using Username) : {}ms", System.currentTimeMillis() - startTime);
        return CompletableFuture.completedFuture(
                ListResponse
                        .builder()
                        .page(profileListRequest.getPage())
                        .size(profileListRequest.getSize())
                        .pageSize(list.size())
                        .totalRecordsCount(searchHits != null ? searchHits.getTotalHits() : 0)
                        .object(list)
                        .build());
    }
}
