package com.bloggios.user.controller;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.user.constants.EndpointConstants;
import com.bloggios.user.payload.response.FollowCountResponse;
import com.bloggios.user.service.FollowAuthService;
import com.bloggios.user.utils.AsyncUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.controller
 * Created_on - June 02 - 2024
 * Created_at - 20:54
 */

@RestController
@RequestMapping(EndpointConstants.FollowAuthController.BASE_PATH)
public class FollowAuthController {

    private final FollowAuthService followAuthService;

    public FollowAuthController(
            FollowAuthService followAuthService
    ) {
        this.followAuthService = followAuthService;
    }

    @GetMapping(EndpointConstants.FollowController.COUNT_FOLLOW)
    public ResponseEntity<FollowCountResponse> followCount(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(followAuthService.countFollowerFollowing(authenticatedUser)));
    }
}
