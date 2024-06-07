package com.bloggios.user.controller;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;
import com.bloggios.user.constants.EndpointConstants;
import com.bloggios.user.payload.request.ProfileListRequest;
import com.bloggios.user.payload.response.ProfileResponse;
import com.bloggios.user.payload.response.ProfileTagResponse;
import com.bloggios.user.service.ProfileAuthService;
import com.bloggios.user.utils.AsyncUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    private static final Logger logger = LoggerFactory.getLogger(ProfileAuthController.class);

    private final ProfileAuthService profileAuthService;

    public ProfileAuthController(
            ProfileAuthService profileAuthService
    ) {
        this.profileAuthService = profileAuthService;
    }

    @GetMapping(EndpointConstants.ProfileAuthController.PROFILE_TAGS)
    public ResponseEntity<ProfileTagResponse> getProfileTags(HttpServletRequest request) {
        logger.info("Header : {}", request.getHeader("bloggios-cookie-mgmt-token"));
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(profileAuthService.getProfileTags()));
    }

    @PostMapping
    public ResponseEntity<ListResponse> getProfileList(@RequestBody ProfileListRequest profileListRequest) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(profileAuthService.getProfileList(profileListRequest)));
    }

    @GetMapping
    public ResponseEntity<ProfileResponse> getMyProfile(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(profileAuthService.getMyProfile(authenticatedUser)));
    }
    @GetMapping("/{email1}")
    public ResponseEntity<ProfileResponse> getResponseByEmail(@RequestParam String email,@PathVariable String email1){
        return ResponseEntity.ok(AsyncUtils.getAsyncResult((profileAuthService.getResponseByEmail(email))));
    }
}
