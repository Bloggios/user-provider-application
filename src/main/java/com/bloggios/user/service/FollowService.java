package com.bloggios.user.service;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.user.payload.response.ModuleResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.service
 * Created_on - 29 January-2024
 * Created_at - 19 : 09
 */

public interface FollowService {

    CompletableFuture<ModuleResponse> followUser(String userId, AuthenticatedUser authenticatedUser);
    CompletableFuture<ModuleResponse> unfollowUser(String userId, AuthenticatedUser authenticatedUser);
}
