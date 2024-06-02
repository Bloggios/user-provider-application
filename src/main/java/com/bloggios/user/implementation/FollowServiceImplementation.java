package com.bloggios.user.implementation;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.user.constants.DataErrorCodes;
import com.bloggios.user.constants.EnvironmentConstants;
import com.bloggios.user.dao.implementation.esimplementation.FollowDocumentDao;
import com.bloggios.user.dao.implementation.pgsqlimplementation.FollowEntityDao;
import com.bloggios.user.dao.implementation.pgsqlimplementation.ProfileEntityDao;
import com.bloggios.user.enums.DaoStatus;
import com.bloggios.user.exception.payloads.BadRequestException;
import com.bloggios.user.modal.FollowEntity;
import com.bloggios.user.modal.ProfileEntity;
import com.bloggios.user.payload.response.ModuleResponse;
import com.bloggios.user.persistence.FollowEntityToDocumentPersistence;
import com.bloggios.user.service.FollowService;
import com.bloggios.user.utils.AsyncUtils;
import com.bloggios.user.utils.ValueCheckerUtil;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.implementation
 * Created_on - June 02 - 2024
 * Created_at - 20:58
 */

@Service
public class FollowServiceImplementation implements FollowService {

    private final ProfileEntityDao profileEntityDao;
    private final FollowEntityDao followEntityDao;
    private final Environment environment;
    private final FollowEntityToDocumentPersistence followEntityToDocumentPersistence;
    private final FollowDocumentDao followDocumentDao;

    public FollowServiceImplementation(ProfileEntityDao profileEntityDao, FollowEntityDao followEntityDao, Environment environment, FollowEntityToDocumentPersistence followEntityToDocumentPersistence, FollowDocumentDao followDocumentDao) {
        this.profileEntityDao = profileEntityDao;
        this.followEntityDao = followEntityDao;
        this.environment = environment;
        this.followEntityToDocumentPersistence = followEntityToDocumentPersistence;
        this.followDocumentDao = followDocumentDao;
    }

    @Override
    public CompletableFuture<ModuleResponse> followUser(String userId, AuthenticatedUser authenticatedUser) {
        ValueCheckerUtil.isValidUUID(userId, DataErrorCodes.INVALID_USER_ID);
        if (userId.equals(authenticatedUser.getUserId())) {
            throw new BadRequestException(DataErrorCodes.USER_CANNOT_FOLLOW_ITSELF);
        }
        CompletableFuture<ProfileEntity> followByCompletableFuture = CompletableFuture.supplyAsync(() -> profileEntityDao.findByUserId(authenticatedUser.getUserId())
                .orElseThrow(()-> new BadRequestException(DataErrorCodes.USER_NOT_FOUND)));
        CompletableFuture<ProfileEntity> followToCompletableFuture = CompletableFuture.supplyAsync(() -> profileEntityDao.findByUserId(userId)
                .orElseThrow(()-> new BadRequestException(DataErrorCodes.FOLLOW_TO_USER_NOT_FOUND)));
        AsyncUtils.getAsyncResult(CompletableFuture.allOf(followByCompletableFuture, followToCompletableFuture));
        ProfileEntity followBy = followByCompletableFuture.join();
        ProfileEntity followTo = followToCompletableFuture.join();
        if (followEntityDao.findByFollowerAndFollowing(followBy, followTo).isPresent()) {
            throw new BadRequestException(DataErrorCodes.USER_ALREADY_FOLLOWING_THAT_USER);
        }
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
        return CompletableFuture.completedFuture(
                ModuleResponse
                        .builder()
                        .message(String.format("You are now following %s", followTo.getName()))
                        .userId(authenticatedUser.getUserId())
                        .id(followEntity.getFollowId())
                        .build()
        );
    }

    @Override
    public CompletableFuture<ModuleResponse> unfollowUser(String userId, AuthenticatedUser authenticatedUser) {
        ValueCheckerUtil.isValidUUID(userId, DataErrorCodes.INVALID_USER_ID);
        CompletableFuture<ProfileEntity> followByCompletableFuture = CompletableFuture.supplyAsync(() -> profileEntityDao.findByUserId(authenticatedUser.getUserId())
                .orElseThrow(()-> new BadRequestException(DataErrorCodes.USER_NOT_FOUND)));
        CompletableFuture<ProfileEntity> followToCompletableFuture = CompletableFuture.supplyAsync(() -> profileEntityDao.findByUserId(userId)
                .orElseThrow(()-> new BadRequestException(DataErrorCodes.FOLLOW_TO_USER_NOT_FOUND)));
        AsyncUtils.getAsyncResult(CompletableFuture.allOf(followByCompletableFuture, followToCompletableFuture));
        ProfileEntity followBy = followByCompletableFuture.join();
        ProfileEntity followTo = followToCompletableFuture.join();
        FollowEntity followEntity = followEntityDao.findByFollowerAndFollowing(followBy, followTo)
                .orElseThrow(() -> new BadRequestException(DataErrorCodes.USER_NOT_FOLLOWING_EACH_OTHER));
        CompletableFuture.runAsync(() -> followEntityDao.deleteByEntity(followEntity));
        CompletableFuture.runAsync(() -> followDocumentDao.deleteById(followEntity.getFollowId()));
        return CompletableFuture.completedFuture(
                ModuleResponse
                        .builder()
                        .message(String.format("You have unfollowed %s", followTo.getName()))
                        .userId(authenticatedUser.getUserId())
                        .id(followEntity.getFollowId())
                        .build()
        );
    }
}
