package com.bloggios.user.transformer.implementation;

import com.bloggios.user.document.ProfileDocument;
import com.bloggios.user.payload.response.ProfileInternalResponse;
import com.bloggios.user.transformer.Transform;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.transformer.implementation.independent
 * Created_on - June 07 - 2024
 * Created_at - 17:48
 */

@Component
public class ProfileDocumentToProfileInternalResponseTransformer implements Transform<ProfileInternalResponse, ProfileDocument> {

    @Override
    public ProfileInternalResponse transform(ProfileDocument profileDocument) {
        return ProfileInternalResponse
                .builder()
                .name(profileDocument.getName())
                .email(profileDocument.getEmail())
                .profileImage(profileDocument.getProfileImage())
                .profileTag(Objects.nonNull(profileDocument.getProfileTag()) ? profileDocument.getProfileTag().getValue() : null)
                .isBadge(profileDocument.isBadge())
                .build();
    }
}
