package com.bloggios.user.service;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.user.payload.response.FollowCountResponse;
import com.bloggios.user.payload.response.IsFollowingResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.service
 * Created_on - June 02 - 2024
 * Created_at - 20:55
 */

public interface FollowAuthService {

    CompletableFuture<FollowCountResponse> countFollowerFollowing(AuthenticatedUser authenticatedUser);
    CompletableFuture<IsFollowingResponse> isFollowing(String userId, AuthenticatedUser authenticatedUser);
//    CompletableFuture<ListResponse> userFollowers(String userId, Integer page, Integer size);
//    CompletableFuture<ListResponse> userFollowing(String userId, Integer page, Integer size);
//    CompletableFuture<ListResponse> followListWithUser(String key, String userId, Integer page);
}
