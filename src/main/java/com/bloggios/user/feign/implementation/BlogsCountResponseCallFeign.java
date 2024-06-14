package com.bloggios.user.feign.implementation;

import com.bloggios.user.feign.BlogProviderApplicationFeign;
import com.bloggios.user.payload.response.BlogCountResponse;
import com.bloggios.user.payload.response.UserProfileResponse;
import com.bloggios.user.utils.TokenExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.feign.implementation
 * Created_on - June 14 - 2024
 * Created_at - 20:10
 */

@Component
@RequiredArgsConstructor
public class BlogsCountResponseCallFeign {

    private final BlogProviderApplicationFeign blogProviderApplicationFeign;

    public Optional<BlogCountResponse> callFeign(String userId) {
        ResponseEntity<BlogCountResponse> responseEntity = blogProviderApplicationFeign.countBlogInternalResponse(userId);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(responseEntity.getBody());
        }
        return Optional.empty();
    }
}
