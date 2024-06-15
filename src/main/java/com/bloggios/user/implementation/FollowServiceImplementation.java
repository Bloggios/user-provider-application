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
import com.bloggios.user.payload.response.FollowCountResponse;
import com.bloggios.user.payload.response.FollowResponse;
import com.bloggios.user.payload.response.ModuleResponse;
import com.bloggios.user.persistence.FollowEntityToDocumentPersistence;
import com.bloggios.user.processor.implementation.HandleFollowProcessor;
import com.bloggios.user.processor.implementation.HandleUnfollowProcessor;
import com.bloggios.user.service.FollowService;
import com.bloggios.user.utils.AsyncUtils;
import com.bloggios.user.utils.ValueCheckerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
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
@RequiredArgsConstructor
public class FollowServiceImplementation implements FollowService {

    private final ProfileEntityDao profileEntityDao;
    private final FollowEntityDao followEntityDao;
    private final HandleUnfollowProcessor handleUnfollowProcessor;
    private final HandleFollowProcessor handleFollowProcessor;

    @Override
    public CompletableFuture<FollowResponse> handleFollow(String userId, AuthenticatedUser authenticatedUser) {
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
        Optional<FollowEntity> byFollowerAndFollowing = followEntityDao.findByFollowerAndFollowing(followBy, followTo);
        FollowResponse followResponse;
        if (byFollowerAndFollowing.isPresent()) {
            followResponse = handleUnfollowProcessor.process(byFollowerAndFollowing.get(), followTo);
        } else {
            followResponse = handleFollowProcessor.process(followBy, followTo, authenticatedUser);
        }
        return CompletableFuture.completedFuture(followResponse);
    }

    @Override
    public CompletableFuture<FollowCountResponse> countFollowerFollowing(AuthenticatedUser authenticatedUser) {
        ProfileEntity profileEntity = profileEntityDao.findByUserId(authenticatedUser.getUserId())
                .orElseThrow(() -> new BadRequestException(DataErrorCodes.PROFILE_NOT_FOUND));
        int followers = profileEntity.getFollowTo().size();
        int following = profileEntity.getFollowedBy().size();
        return CompletableFuture.completedFuture(
                FollowCountResponse
                        .builder()
                        .followers(followers)
                        .following(following)
                        .userId(authenticatedUser.getUserId())
                        .build()
        );
    }
}
