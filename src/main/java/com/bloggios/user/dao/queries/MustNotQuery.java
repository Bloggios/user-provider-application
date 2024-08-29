package com.bloggios.user.dao.queries;

import com.bloggios.user.payload.request.MustNotQueryListRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.dao.implementation.querybuilder
 * Created_on - 03 February-2024
 * Created_at - 19 : 43
 */

@Component
public class MustNotQuery {

    public NativeSearchQuery build(MustNotQueryListRequest mustNotQueryListRequest) {
        BoolQueryBuilder boolQuery = QueryBuilders
                .boolQuery()
                .mustNot(QueryBuilders.termsQuery(mustNotQueryListRequest.getKey(), mustNotQueryListRequest.getDataToExclude()));

        return new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withTrackTotalHits(Boolean.TRUE)
                .withPageable(PageRequest.of(mustNotQueryListRequest.getPage(), mustNotQueryListRequest.getSize()))
                .build();
    }
}
