package com.bloggios.user.controller;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.user.constants.EndpointConstants;
import com.bloggios.user.payload.response.FollowCountResponse;
import com.bloggios.user.payload.response.ProfileResponse;
import com.bloggios.user.payload.response.ProfileInternalResponse;
import com.bloggios.user.service.FollowService;
import com.bloggios.user.service.ProfileService;
import com.bloggios.user.utils.AsyncUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.controller
 * Created_on - June 07 - 2024
 * Created_at - 17:44
 */

@RestController
@RequestMapping(EndpointConstants.OpenController.BASE_PATH)
@RequiredArgsConstructor
public class OpenController {

    private final ProfileService profileService;
    private final FollowService followService;

    @GetMapping(EndpointConstants.OpenController.PROFILE_INTERNAL_RESPONSE)
    public ResponseEntity<ProfileInternalResponse> getProfileInternalResponse(@PathVariable String userId) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(profileService.getProfileInternalResponse(userId)));
    }

    @GetMapping(EndpointConstants.OpenController.Follow.COUNT_FOLLOW)
    public ResponseEntity<FollowCountResponse> followCount(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(followService.countFollowerFollowing(authenticatedUser)));
    }

    @GetMapping(EndpointConstants.OpenController.Profile.USER_PROFILE)
    public ResponseEntity<ProfileResponse> getUserProfile(@RequestParam String email, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(profileService.getUserProfile(email, authenticatedUser)));
    }

}
