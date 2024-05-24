package com.bloggios.user.feign.implementation;

import com.bloggios.user.feign.AuthProviderApplicationFeign;
import com.bloggios.user.payload.response.UserProfileResponse;
import com.bloggios.user.utils.TokenExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.feign.implementation
 * Created_on - May 24 - 2024
 * Created_at - 12:54
 */

@Component
public class UserProfileResponseFeignCall {

    private final AuthProviderApplicationFeign authProviderApplicationFeign;

    public UserProfileResponseFeignCall(
            AuthProviderApplicationFeign authProviderApplicationFeign
    ) {
        this.authProviderApplicationFeign = authProviderApplicationFeign;
    }

    public Optional<UserProfileResponse> callFeign(HttpServletRequest httpServletRequest) {
        String accessToken = TokenExtractor.extractToken(httpServletRequest);
        ResponseEntity<UserProfileResponse> responseEntity = authProviderApplicationFeign.userProfileResponse(accessToken);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(responseEntity.getBody());
        }
        return Optional.empty();
    }
}
