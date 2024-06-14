package com.bloggios.user.controller;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.user.constants.EndpointConstants;
import com.bloggios.user.implementation.FollowServiceImplementation;
import com.bloggios.user.payload.response.FollowResponse;
import com.bloggios.user.payload.response.ModuleResponse;
import com.bloggios.user.service.FollowService;
import com.bloggios.user.utils.AsyncUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.controller
 * Created_on - June 02 - 2024
 * Created_at - 20:50
 */

@RestController
@RequestMapping(EndpointConstants.FollowController.BASE_PATH)
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @GetMapping(EndpointConstants.FollowController.HANDLE_FOLLOW)
    public ResponseEntity<FollowResponse> handleFollow(@PathVariable String userId, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(followService.handleFollow(userId, authenticatedUser)));
    }
}
