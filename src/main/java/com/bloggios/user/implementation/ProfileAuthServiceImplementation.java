package com.bloggios.user.implementation;

import com.bloggios.elasticsearch.configuration.payload.ListRequest;
import com.bloggios.user.constants.BeanConstants;
import com.bloggios.user.constants.DataErrorCodes;
import com.bloggios.user.dao.implementation.esimplementation.ProfileDocumentDao;
import com.bloggios.user.document.ProfileDocument;
import com.bloggios.user.enums.ProfileTag;
import com.bloggios.user.exception.payloads.BadRequestException;
import com.bloggios.user.payload.request.ProfileListRequest;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;
import com.bloggios.user.payload.response.ProfileTagResponse;
import com.bloggios.user.service.ProfileAuthService;
import com.bloggios.user.transformer.implementation.ProfileListToListRequestTransformer;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.implementation
 * Created_on - May 26 - 2024
 * Created_at - 15:53
 */

@Service
public class ProfileAuthServiceImplementation implements ProfileAuthService {

    private final ProfileListToListRequestTransformer profileListToListRequestTransformer;
    private final ProfileDocumentDao profileDocumentDao;

    public ProfileAuthServiceImplementation(
            ProfileListToListRequestTransformer profileListToListRequestTransformer,
            ProfileDocumentDao profileDocumentDao) {
        this.profileListToListRequestTransformer = profileListToListRequestTransformer;
        this.profileDocumentDao = profileDocumentDao;
    }

    @Override
    @Async(BeanConstants.ASYNC_TASK_EXTERNAL_POOL)
    public CompletableFuture<ProfileTagResponse> getProfileTags() {
        List<String> tags = Arrays
                .stream(ProfileTag.values())
                .parallel()
                .map(ProfileTag::getValue)
                .toList();
        return CompletableFuture.completedFuture(
                ProfileTagResponse
                        .builder()
                        .tags(tags)
                        .totalRecordCounts(tags.size())
                        .build()
        );
    }

    @Override
    public CompletableFuture<ListResponse> getProfileList(ProfileListRequest profileListRequest) {
        if (Objects.isNull(profileListRequest)) throw new BadRequestException(DataErrorCodes.USER_LIST_REQUEST_NULL);
        ListRequest transform = profileListToListRequestTransformer.transform(profileListRequest);
        SearchHits<ProfileDocument> searchHits = profileDocumentDao.profileDocumentSearchHits(transform);
        List<ProfileDocument> list = new ArrayList<>();
        if (Objects.nonNull(searchHits)) {
            list = searchHits
                    .stream()
                    .map(SearchHit::getContent)
                    .toList();
        }
        return CompletableFuture.completedFuture(
                ListResponse
                        .builder()
                        .page(profileListRequest.getPage())
                        .size(profileListRequest.getSize())
                        .pageSize(list.size())
                        .totalRecordsCount(searchHits!=null ? searchHits.getTotalHits() : 0)
                        .object(list)
                        .build());
    }
}
