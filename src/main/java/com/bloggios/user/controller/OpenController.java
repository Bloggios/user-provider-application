package com.bloggios.user.controller;

import com.bloggios.user.constants.EndpointConstants;
import com.bloggios.user.payload.response.ProfileInternalResponse;
import com.bloggios.user.service.ProfileService;
import com.bloggios.user.utils.AsyncUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(EndpointConstants.OpenController.PROFILE_INTERNAL_RESPONSE)
    public ResponseEntity<ProfileInternalResponse> getProfileInternalResponse(@PathVariable String userId) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(profileService.getProfileInternalResponse(userId)));
    }

}
