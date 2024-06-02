package com.bloggios.user.persistence;

import com.bloggios.user.dao.implementation.esimplementation.FollowDocumentDao;
import com.bloggios.user.document.FollowDocument;
import com.bloggios.user.enums.DaoStatus;
import com.bloggios.user.modal.FollowEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.processor.implementation.voidprocess
 * Created_on - 29 January-2024
 * Created_at - 20 : 20
 */

@Component
public class FollowEntityToDocumentPersistence {

    private final ModelMapper modelMapper;
    private final FollowDocumentDao followDocumentDao;

    public FollowEntityToDocumentPersistence(ModelMapper modelMapper, FollowDocumentDao followDocumentDao) {
        this.modelMapper = modelMapper;
        this.followDocumentDao = followDocumentDao;
    }

    public void persist(FollowEntity followEntity, DaoStatus daoStatus) {
        FollowDocument followDocument = modelMapper.map(followEntity, FollowDocument.class);
        followDocument.setFollowTo(followEntity.getFollowTo().getUserId());
        followDocument.setFollowedBy(followEntity.getFollowedBy().getUserId());
        followDocumentDao.initOperation(daoStatus, followDocument);
    }
}
