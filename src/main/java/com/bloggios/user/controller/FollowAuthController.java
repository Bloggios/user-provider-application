package com.bloggios.user.controller;

import com.bloggios.user.constants.EndpointConstants;
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

//    @GetMapping(EndpointConstants.FollowController.COUNT_FOLLOW)
//    public ResponseEntity<FollowCountResponse> followCount(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
//        return ResponseEntity.ok(AsyncUtils.getAsyncResult(followService.countFollowerFollowing(authenticatedUser)));
//    }
}
