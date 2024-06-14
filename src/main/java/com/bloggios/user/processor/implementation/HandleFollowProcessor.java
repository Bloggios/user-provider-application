package com.bloggios.user.processor.implementation;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.user.constants.EnvironmentConstants;
import com.bloggios.user.constants.ResponseMessageConstants;
import com.bloggios.user.dao.implementation.pgsqlimplementation.FollowEntityDao;
import com.bloggios.user.enums.DaoStatus;
import com.bloggios.user.modal.FollowEntity;
import com.bloggios.user.modal.ProfileEntity;
import com.bloggios.user.payload.response.FollowResponse;
import com.bloggios.user.payload.response.ModuleResponse;
import com.bloggios.user.persistence.FollowEntityToDocumentPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.processor.implementation
 * Created_on - June 11 - 2024
 * Created_at - 20:23
 */

@Component
@RequiredArgsConstructor
public class HandleFollowProcessor {

    private final Environment environment;
    private final FollowEntityDao followEntityDao;
    private final FollowEntityToDocumentPersistence followEntityToDocumentPersistence;

    public FollowResponse process(ProfileEntity followBy, ProfileEntity followTo, AuthenticatedUser authenticatedUser) {
        FollowEntity followEntity = FollowEntity
                .builder()
                .followedBy(followBy)
                .followTo(followTo)
                .userId(authenticatedUser.getUserId())
                .apiVersion(UUID.randomUUID().toString())
                .version(environment.getProperty(EnvironmentConstants.APPLICATION_VERSION))
                .followedOn(Date.from(Instant.now()))
                .build();
        FollowEntity followResponseEntity = followEntityDao.initOperation(DaoStatus.CREATE, followEntity);
        CompletableFuture.runAsync(() -> followEntityToDocumentPersistence.persist(followResponseEntity, DaoStatus.CREATE));
        return FollowResponse
                .builder()
                .message(String.format(ResponseMessageConstants.FOLLOWED, followTo.getName()))
                .isFollow(true)
                .build();
    }
}
