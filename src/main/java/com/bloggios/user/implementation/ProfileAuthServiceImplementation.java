package com.bloggios.user.implementation;

import com.bloggios.user.constants.BeanConstants;
import com.bloggios.user.enums.ProfileTag;
import com.bloggios.user.payload.response.ProfileTagResponse;
import com.bloggios.user.service.ProfileAuthService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.implementation
 * Created_on - May 26 - 2024
 * Created_at - 15:53
 */

@Service
public class ProfileAuthServiceImplementation implements ProfileAuthService {

    @Override
    @Async(BeanConstants.ASYNC_TASK_EXTERNAL_POOL)
    public CompletableFuture<ProfileTagResponse> getProfileTags() {
        List<String> tags = Arrays
                .stream(ProfileTag.values())
                .parallel()
                .map(ProfileTag::getValue)
                .toList();
        return CompletableFuture.completedFuture(
                ProfileTagResponse
                        .builder()
                        .tags(tags)
                        .totalRecordCounts(tags.size())
                        .build()
        );
    }
}
