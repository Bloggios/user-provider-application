package com.bloggios.user.transformer.implementation;

import com.bloggios.user.modal.ProfileEntity;
import com.bloggios.user.payload.response.BlogCountResponse;
import com.bloggios.user.payload.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.transformer.implementation
 * Created_on - June 14 - 2024
 * Created_at - 20:06
 */

@Component
@RequiredArgsConstructor
public class ProfileEntityToProfileResponseTransformer {

    private final ModelMapper modelMapper;

    public ProfileResponse transform(ProfileEntity profileEntity, BlogCountResponse blogCountResponse) {
        ProfileResponse response = modelMapper.map(profileEntity, ProfileResponse.class);
        response.setBlogs(blogCountResponse.getBlogs());
        response.setFollowCount(profileEntity.getFollowedBy().size());
        response.setFollowingCount(profileEntity.getFollowTo().size());
        return response;
    }
}
