package com.bloggios.user.transformer.implementation;

import com.bloggios.user.document.ProfileDocument;
import com.bloggios.user.payload.response.FollowCountResponse;
import com.bloggios.user.payload.response.ProfileInternalResponse;
import com.bloggios.user.processor.fetch.FetchUserFollowFollowingCount;
import com.bloggios.user.transformer.Transform;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ProfileDocumentToProfileInternalResponseTransformer implements Transform<ProfileInternalResponse, ProfileDocument> {

    private final FetchUserFollowFollowingCount fetchUserFollowFollowingCount;

    @Override
    public ProfileInternalResponse transform(ProfileDocument profileDocument) {
        FollowCountResponse followCountResponse = fetchUserFollowFollowingCount.fetch(profileDocument.getUserId());
        return ProfileInternalResponse
                .builder()
                .name(profileDocument.getName())
                .email(profileDocument.getEmail())
                .profileImage(profileDocument.getProfileImage())
                .profileTag(Objects.nonNull(profileDocument.getProfileTag()) ? profileDocument.getProfileTag().getValue() : null)
                .isBadge(profileDocument.isBadge())
                .followers(followCountResponse.getFollowers())
                .following(followCountResponse.getFollowing())
                .build();
    }
}
