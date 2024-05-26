package com.bloggios.user.controller;

import com.bloggios.user.constants.EndpointConstants;
import com.bloggios.user.payload.response.ProfileTagResponse;
import com.bloggios.user.service.ProfileAuthService;
import com.bloggios.user.utils.AsyncUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class ProfileAuthController {

    private final ProfileAuthService profileAuthService;

    public ProfileAuthController(
            ProfileAuthService profileAuthService
    ) {
        this.profileAuthService = profileAuthService;
    }

    @GetMapping(EndpointConstants.ProfileAuthController.PROFILE_TAGS)
    public ResponseEntity<ProfileTagResponse> getProfileTags() {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(profileAuthService.getProfileTags()));
    }
}
