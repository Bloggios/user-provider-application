package com.bloggios.user.transformer.implementation;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.user.enums.ProfileTag;
import com.bloggios.user.modal.ProfileEntity;
import com.bloggios.user.payload.request.ProfileRequest;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

import static com.bloggios.user.constants.EnvironmentConstants.APPLICATION_VERSION;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.transformer.implementation
 * Created_on - May 24 - 2024
 * Created_at - 12:17
 */

@Component
public class ProfileRequestToProfileEntityTransformer {

    private final Environment environment;

    public ProfileRequestToProfileEntityTransformer(
            Environment environment
    ) {
        this.environment = environment;
    }

    public ProfileEntity transform(ProfileRequest profileRequest, AuthenticatedUser authenticatedUser) {
        return ProfileEntity
                .builder()
                .name(profileRequest.getName())
                .email(authenticatedUser.getEmail())
                .username(authenticatedUser.getUsername())
                .bio(profileRequest.getBio())
                .link(profileRequest.getLink())
                .userId(authenticatedUser.getUserId())
                .createdOn(new Date())
                .version(UUID.randomUUID().toString())
                .apiVersion(environment.getProperty(APPLICATION_VERSION))
                .profileTag(StringUtils.hasText(profileRequest.getProfileTag()) ? ProfileTag.getByValue(profileRequest.getProfileTag()) : null)
                .build();
    }
}
