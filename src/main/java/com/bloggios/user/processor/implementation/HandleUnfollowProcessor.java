package com.bloggios.user.processor.implementation;

import com.bloggios.user.constants.ResponseMessageConstants;
import com.bloggios.user.dao.implementation.esimplementation.FollowDocumentDao;
import com.bloggios.user.dao.implementation.pgsqlimplementation.FollowEntityDao;
import com.bloggios.user.modal.FollowEntity;
import com.bloggios.user.modal.ProfileEntity;
import com.bloggios.user.payload.response.FollowResponse;
import com.bloggios.user.payload.response.ModuleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.processor.implementation
 * Created_on - June 11 - 2024
 * Created_at - 20:29
 */

@Component
@RequiredArgsConstructor
public class HandleUnfollowProcessor {

    private final FollowEntityDao followEntityDao;
    private final FollowDocumentDao followDocumentDao;

    public FollowResponse process(FollowEntity followEntity, ProfileEntity followTo) {
        CompletableFuture.runAsync(() -> followEntityDao.deleteByEntity(followEntity));
        CompletableFuture.runAsync(() -> followDocumentDao.deleteById(followEntity.getFollowId()));
        return FollowResponse
                .builder()
                .message(String.format(ResponseMessageConstants.UNFOLLOWED, followTo.getName()))
                .isFollow(false)
                .build();
    }
}
