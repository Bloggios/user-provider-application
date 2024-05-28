package com.bloggios.user.dao.repository.elasticsearch;

import com.bloggios.user.document.ProfileDocument;
import com.bloggios.user.payload.response.ProfileResponse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.dao.repository.elasticsearch
 * Created_on - May 24 - 2024
 * Created_at - 12:07
 */

public interface ProfileDocumentRepository extends ElasticsearchRepository<ProfileDocument, String> {

    Optional<ProfileDocument> findByUserId(String userId);
}
