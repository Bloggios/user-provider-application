package com.bloggios.user.dao.implementation.esimplementation;

import com.bloggios.elasticsearch.configuration.elasticdao.ElasticQuery;
import com.bloggios.elasticsearch.configuration.payload.ListRequest;
import com.bloggios.user.constants.EnvironmentConstants;
import com.bloggios.user.constants.InternalErrorCodes;
import com.bloggios.user.dao.EsAbstractDao;
import com.bloggios.user.dao.repository.elasticsearch.FollowDocumentRepository;
import com.bloggios.user.document.FollowDocument;
import com.bloggios.user.exception.payloads.BadRequestException;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.dao.implementation.elasticsearch
 * Created_on - 30 January-2024
 * Created_at - 12 : 14
 */

@Component
public class FollowDocumentDao extends EsAbstractDao<FollowDocument, FollowDocumentRepository> {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticQuery elasticQuery;
    private final Environment environment;

    protected FollowDocumentDao(
            FollowDocumentRepository repository,
            ElasticsearchOperations elasticsearchOperations,
            ElasticQuery elasticQuery,
            Environment environment
    ) {
        super(repository);
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticQuery = elasticQuery;
        this.environment = environment;
    }

    @Override
    protected FollowDocument initCreate(FollowDocument followDocument) {
        if (Objects.isNull(followDocument.getFollowedOn())) followDocument.setFollowedOn(Date.from(Instant.now()));
        return super.initCreate(followDocument);
    }

    public void deleteById(String followId) {
        try {
            repository.deleteById(followId);
        } catch (Exception exception) {
            throw new BadRequestException(InternalErrorCodes.FAILED_TO_DELETE_FOLLOW_ES);
        }
    }

    public SearchHits<FollowDocument> followList(ListRequest listRequest) {
        return elasticsearchOperations
                .search(elasticQuery.build(listRequest), FollowDocument.class, IndexCoordinates.of(
                        environment.getProperty(EnvironmentConstants.FOLLOW_DOCUMENT_INDEX)
                ));
    }
}
