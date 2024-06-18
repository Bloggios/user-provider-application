package com.bloggios.user.controller;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;
import com.bloggios.user.constants.EndpointConstants;
import com.bloggios.user.payload.request.ProfileListRequest;
import com.bloggios.user.payload.response.ProfileResponse;
import com.bloggios.user.payload.response.ProfileTagResponse;
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
 * Package - com.bloggios.user.controller
 * Created_on - May 26 - 2024
 * Created_at - 15:50
 */

@RestController
@RequestMapping(EndpointConstants.ProfileAuthController.BASE_PATH)
@RequiredArgsConstructor
public class ProfileAuthController {

    private final ProfileService profileService;

    @GetMapping(EndpointConstants.ProfileAuthController.PROFILE_TAGS)
    public ResponseEntity<ProfileTagResponse> getProfileTags() {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(profileService.getProfileTags()));
    }

    @PostMapping
    public ResponseEntity<ListResponse> getProfileList(@RequestBody ProfileListRequest profileListRequest) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(profileService.getProfileList(profileListRequest)));
    }

    @GetMapping
    public ResponseEntity<ProfileResponse> getMyProfile(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(profileService.getMyProfile(authenticatedUser)));
    }
}
