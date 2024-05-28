package com.bloggios.user.transformer.implementation;

import com.bloggios.elasticsearch.configuration.constants.ElasticServiceConstants;
import com.bloggios.elasticsearch.configuration.payload.ListRequest;
import com.bloggios.elasticsearch.configuration.payload.lspayload.Filter;
import com.bloggios.elasticsearch.configuration.payload.lspayload.SearchFilterNgram;
import com.bloggios.elasticsearch.configuration.payload.lspayload.Sort;
import com.bloggios.user.constants.DataErrorCodes;
import com.bloggios.user.constants.ServiceConstants;
import com.bloggios.user.exception.payloads.BadRequestException;
import com.bloggios.elasticsearch.configuration.payload.YamlListDataProvider;
import com.bloggios.user.payload.request.ProfileListRequest;
import com.bloggios.user.transformer.Transform;
import com.bloggios.user.utils.AsyncUtils;
import com.bloggios.user.utils.ListRequestUtils;
import com.bloggios.user.ymlparser.listparser.ProfileYmlFileParser;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.transformer.implementation
 * Created_on - May 27 - 2024
 * Created_at - 12:55
 */

@Component
public class ProfileListToListRequestTransformer implements Transform<ListRequest, ProfileListRequest> {

    private final ProfileYmlFileParser profileYmlFileParser;

    public ProfileListToListRequestTransformer(
            ProfileYmlFileParser profileYmlFileParser
    ) {
        this.profileYmlFileParser = profileYmlFileParser;
    }

    @Override
    public ListRequest transform(ProfileListRequest profileListRequest) {
        Map<String, YamlListDataProvider> provider = profileYmlFileParser.getProvider();
        CompletableFuture<List<Filter>> filterListCompletableFuture = CompletableFuture.supplyAsync(() -> filterTransform(profileListRequest, provider));
        CompletableFuture<List<Sort>> sortListCompletableFuture = CompletableFuture.supplyAsync(() -> transformSort(profileListRequest, provider));
        Function<ProfileListRequest, List<String>> getSearch = commentList ->
                Optional.ofNullable(profileListRequest.getTexts())
                        .stream()
                        .flatMap(Collection::stream)
                        .filter(Objects::nonNull)
                        .toList();
        List<String> searchTexts = getSearch.apply(profileListRequest);
        ListRequest listRequest = new ListRequest();
        AsyncUtils.getAsyncResult(CompletableFuture.allOf(filterListCompletableFuture, sortListCompletableFuture));
        listRequest.setFilters(filterListCompletableFuture.join());
        listRequest.setSort(sortListCompletableFuture.join());
        if (!CollectionUtils.isEmpty(searchTexts)) {
            List<SearchFilterNgram> searchFilterNgrams = transformSearchNgram(profileListRequest, provider);
            listRequest.setSearchTextsNgram(searchTexts);
            listRequest.setSearchFieldsNgram(searchFilterNgrams);
        }
        if (Objects.nonNull(profileListRequest.getPage())) listRequest.setPage(profileListRequest.getPage());
        if (Objects.nonNull(profileListRequest.getSize())) listRequest.setSize(profileListRequest.getSize());
        return listRequest;
    }

    private List<Filter> filterTransform(ProfileListRequest profileListRequest, Map<String, YamlListDataProvider> provider) {
        List<Filter> filters = new ArrayList<>();
        if (Objects.nonNull(profileListRequest.getFilters())) {
            profileListRequest
                    .getFilters()
                    .stream()
                    .filter(Objects::nonNull)
                    .forEach(filter -> {
                        String columnName = null;
                        String nestedPath = null;
                        String filterKeyType = null;
                        Boolean partialSearch = true;
                        if (provider.containsKey(filter.getFilterKey())) {
                            YamlListDataProvider value = provider.get(filter.getFilterKey());
                            columnName = value.getFilterField();
                            nestedPath = value.getInnerPath();
                            filterKeyType = value.getDataType();
                            if (!ObjectUtils.isEmpty(value.getPartialSearch())) {
                                partialSearch = value.getPartialSearch();
                            } else {
                                partialSearch = ElasticServiceConstants.STRING.equalsIgnoreCase(value.getDataType());
                            }
                        } else {
                            throw new BadRequestException(DataErrorCodes.FILTER_KEY_NOT_VALID);
                        }
                        filters.add(ListRequestUtils.getFilter(filter.getSelections(), columnName, nestedPath, filterKeyType, partialSearch));
                    });
        }
        return filters;
    }

    private List<SearchFilterNgram> transformSearchNgram(ProfileListRequest profileListRequest, Map<String, YamlListDataProvider> provider) {
        List<SearchFilterNgram> searchFilterNgrams = new ArrayList<>();
        provider
                .entrySet()
                .forEach(type -> {
                    if (Boolean.TRUE.equals(type.getValue().getSearchAllowed())) {
                        isPartialSearch(type, searchFilterNgrams);
                    }
                });
        return searchFilterNgrams;
    }

    private void isPartialSearch(Map.Entry<String, YamlListDataProvider> provider, List<SearchFilterNgram> searchFilterNgrams) {
        if (Objects.nonNull(provider.getValue().getPartialSearch())) {
            searchFilterNgrams.add(ListRequestUtils.getSearchFilterNgram(provider.getValue().getSearchField(), provider.getValue().getInnerPath(), provider.getValue().getPartialSearch()));
        } else {
            searchFilterNgrams.add(ListRequestUtils.getSearchFilterNgram(provider.getValue().getSearchField(), provider.getValue().getInnerPath(), ElasticServiceConstants.STRING.equalsIgnoreCase(provider.getValue().getDataType())));
        }
    }

    private List<Sort> transformSort(ProfileListRequest profileListRequest, Map<String, YamlListDataProvider> provider) {
        List<Sort> sorts = new ArrayList<>();
        if (!ObjectUtils.isEmpty(profileListRequest.getSorts()) && !profileListRequest.getSorts().isEmpty()) {
            profileListRequest
                    .getSorts()
                    .stream()
                    .filter(Objects::nonNull)
                    .forEach(sort -> {
                        String columnName = null;
                        String nestedPath = null;
                        if (provider.containsKey(sort.getSortKey())) {
                            YamlListDataProvider value = provider.get(sort.getSortKey());
                            columnName = value.getSortField();
                            nestedPath = value.getInnerPath();
                        } else {
                            throw new BadRequestException(DataErrorCodes.SORT_KEY_NOT_VALID);
                        }
                        sorts.add(ListRequestUtils.getSort(sort, columnName, nestedPath));
                    });
        } else {
            return ListRequestUtils.getSort(ServiceConstants.CREATED_ON);
        }
        return sorts;
    }
}
