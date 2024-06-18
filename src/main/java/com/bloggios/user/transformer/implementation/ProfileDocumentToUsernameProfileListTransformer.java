package com.bloggios.user.transformer.implementation;

import com.bloggios.user.document.ProfileDocument;
import com.bloggios.user.payload.response.ProfileResponse;
import com.bloggios.user.payload.response.UserProfileResponse;
import com.bloggios.user.transformer.Transform;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.transformer.implementation
 * Created_on - June 18 - 2024
 * Created_at - 18:04
 */

@Component
public class ProfileDocumentToUsernameProfileListTransformer implements Transform<ProfileResponse, ProfileDocument> {

    @Override
    public ProfileResponse transform(ProfileDocument profileDocument) {
        return ProfileResponse
                .builder()
                .userId(profileDocument.getUserId())
                .name(profileDocument.getName())
                .email(profileDocument.getEmail())
                .username(profileDocument.getEmail())
                .isBadge(profileDocument.isBadge())
                .userBadge(profileDocument.getUserBadge())
                .build();
    }
}
