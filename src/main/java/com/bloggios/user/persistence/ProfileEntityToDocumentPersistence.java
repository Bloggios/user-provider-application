package com.bloggios.user.persistence;

import com.bloggios.user.dao.implementation.esimplementation.ProfileDocumentDao;
import com.bloggios.user.document.ProfileDocument;
import com.bloggios.user.enums.DaoStatus;
import com.bloggios.user.modal.ProfileEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.persistence
 * Created_on - May 24 - 2024
 * Created_at - 13:40
 */

@Component
public class ProfileEntityToDocumentPersistence {

    private final ModelMapper modelMapper;
    private final ProfileDocumentDao profileDocumentDao;

    public ProfileEntityToDocumentPersistence(
            ModelMapper modelMapper,
            ProfileDocumentDao profileDocumentDao
    ) {
        this.modelMapper = modelMapper;
        this.profileDocumentDao = profileDocumentDao;
    }

    public ProfileDocument persist(ProfileEntity profileEntity, DaoStatus daoStatus) {
        ProfileDocument profileDocument = modelMapper.map(profileEntity, ProfileDocument.class);
        return profileDocumentDao.initOperation(daoStatus, profileDocument);
    }
}
