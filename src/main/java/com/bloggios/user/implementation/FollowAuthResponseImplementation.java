package com.bloggios.user.implementation;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.user.constants.DataErrorCodes;
import com.bloggios.user.dao.implementation.pgsqlimplementation.ProfileEntityDao;
import com.bloggios.user.exception.payloads.BadRequestException;
import com.bloggios.user.modal.ProfileEntity;
import com.bloggios.user.payload.response.FollowCountResponse;
import com.bloggios.user.payload.response.IsFollowingResponse;
import com.bloggios.user.service.FollowAuthService;
import com.bloggios.user.utils.ValueCheckerUtil;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.implementation
 * Created_on - June 03 - 2024
 * Created_at - 21:31
 */

@Service
public class FollowAuthResponseImplementation implements FollowAuthService {

    private final ProfileEntityDao profileEntityDao;

    public FollowAuthResponseImplementation(ProfileEntityDao profileEntityDao) {
        this.profileEntityDao = profileEntityDao;
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
