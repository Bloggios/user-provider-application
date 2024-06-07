package com.bloggios.user.dao.implementation.esimplementation;

import com.bloggios.elasticsearch.configuration.elasticdao.ElasticQuery;
import com.bloggios.elasticsearch.configuration.payload.ListRequest;
import com.bloggios.user.constants.EnvironmentConstants;
import com.bloggios.user.dao.EsAbstractDao;
import com.bloggios.user.dao.repository.elasticsearch.ProfileDocumentRepository;
import com.bloggios.user.document.ProfileDocument;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticQuery elasticQuery;
    private final Environment environment;

    protected ProfileDocumentDao(
            ProfileDocumentRepository repository,
            ElasticsearchOperations elasticsearchOperations,
            ElasticQuery elasticQuery,
            Environment environment
    ) {
        super(repository);
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticQuery = elasticQuery;
        this.environment = environment;
    }

    public SearchHits<ProfileDocument> profileDocumentSearchHits(ListRequest listRequest) {
        return elasticsearchOperations
                .search(elasticQuery.build(listRequest), ProfileDocument.class, IndexCoordinates.of(
                        environment.getProperty(EnvironmentConstants.PROFILE_INDEX)
                ));
    }

    public Optional<ProfileDocument> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public Optional<ProfileDocument> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
