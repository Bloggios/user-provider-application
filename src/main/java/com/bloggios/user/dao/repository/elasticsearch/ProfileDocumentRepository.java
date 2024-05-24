package com.bloggios.user.dao.repository.elasticsearch;

import com.bloggios.user.document.ProfileDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.dao.repository.elasticsearch
 * Created_on - May 24 - 2024
 * Created_at - 12:07
 */

public interface ProfileDocumentRepository extends ElasticsearchRepository<ProfileDocument, String> {
}
