package com.bloggios.user.feign;

import com.bloggios.user.constants.EndpointConstants;
import com.bloggios.user.constants.EnvironmentConstants;
import com.bloggios.user.constants.ServiceConstants;
import com.bloggios.user.payload.response.UserProfileResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.feign
 * Created_on - May 24 - 2024
 * Created_at - 12:25
 */

@FeignClient(
        name = ServiceConstants.AUTH_PROVIDER_APPLICATION,
        url = EnvironmentConstants.BASE_PATH
)
@Headers("Authorization: {token}")
public interface AuthProviderApplicationFeign {

    @PostMapping(EndpointConstants.FeignClient.USER_PROFILE_RESPONSE)
    ResponseEntity<UserProfileResponse> userProfileResponse(@RequestHeader(ServiceConstants.AUTHORIZATION) String token);
}
