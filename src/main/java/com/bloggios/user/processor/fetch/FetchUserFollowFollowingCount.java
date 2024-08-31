package com.bloggios.user.processor.fetch;

import com.bloggios.user.constants.DataErrorCodes;
import com.bloggios.user.dao.implementation.pgsqlimplementation.ProfileEntityDao;
import com.bloggios.user.exception.payloads.BadRequestException;
import com.bloggios.user.modal.ProfileEntity;
import com.bloggios.user.payload.response.FollowCountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.processor.implementation
 * Created_on - August 31 - 2024
 * Created_at - 16:45
 */

@Component
@RequiredArgsConstructor
public class FetchUserFollowFollowingCount {

    private final ProfileEntityDao profileEntityDao;

    public FollowCountResponse fetch(String userId) {
        ProfileEntity profileEntity = profileEntityDao.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException(DataErrorCodes.PROFILE_NOT_FOUND));
        int followers = profileEntity.getFollowTo().size();
        int following = profileEntity.getFollowedBy().size();
        return FollowCountResponse
                        .builder()
                        .followers(followers)
                        .following(following)
                        .userId(userId)
                        .build();
    }
}
