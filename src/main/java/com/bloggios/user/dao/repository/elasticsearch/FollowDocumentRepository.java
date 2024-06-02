package com.bloggios.user.dao.repository.elasticsearch;

import com.bloggios.user.document.FollowDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.dao.repository.esrepository
 * Created_on - 30 January-2024
 * Created_at - 12 : 12
 */

public interface FollowDocumentRepository extends ElasticsearchRepository<FollowDocument, String> {
}
