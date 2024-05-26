package com.bloggios.user.controller;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.user.constants.EndpointConstants;
import com.bloggios.user.payload.request.ProfileRequest;
import com.bloggios.user.payload.response.ModuleResponse;
import com.bloggios.user.service.ProfileService;
import com.bloggios.user.utils.AsyncUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.controller
 * Created_on - May 23 - 2024
 * Created_at - 12:33
 */

@RestController
@RequestMapping(EndpointConstants.ProfileController.BASE_PATH)
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(
            ProfileService profileService
    ) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<ModuleResponse> addProfile(@RequestBody ProfileRequest profileRequest, @AuthenticationPrincipal AuthenticatedUser authenticatedUser, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(profileService.addProfile(profileRequest, authenticatedUser, httpServletRequest)));
    }
}
