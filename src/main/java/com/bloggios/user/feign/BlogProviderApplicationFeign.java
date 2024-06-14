package com.bloggios.user.feign;

import com.bloggios.user.constants.EndpointConstants;
import com.bloggios.user.constants.EnvironmentConstants;
import com.bloggios.user.constants.ServiceConstants;
import com.bloggios.user.payload.response.BlogCountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.feign
 * Created_on - June 14 - 2024
 * Created_at - 20:07
 */

@FeignClient(
        name = ServiceConstants.BLOG_PROVIDER_APPLICATION,
        url = EnvironmentConstants.BASE_PATH
)
public interface BlogProviderApplicationFeign {

    @GetMapping(EndpointConstants.FeignClient.BLOG_COUNT_INTERNAL_RESPONSE)
    ResponseEntity<BlogCountResponse> countBlogInternalResponse(@RequestParam String userId);
}
