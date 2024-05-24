package com.bloggios.user.dao.implementation.esimplementation;

import com.bloggios.user.dao.EsAbstractDao;
import com.bloggios.user.dao.repository.elasticsearch.ProfileDocumentRepository;
import com.bloggios.user.document.ProfileDocument;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.dao.implementation.esimplementation
 * Created_on - May 24 - 2024
 * Created_at - 12:09
 */

@Component
public class ProfileDocumentDao extends EsAbstractDao<ProfileDocument, ProfileDocumentRepository> {

    protected ProfileDocumentDao(ProfileDocumentRepository repository) {
        super(repository);
    }
}
