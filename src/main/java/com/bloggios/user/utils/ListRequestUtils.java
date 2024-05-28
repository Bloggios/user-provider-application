/*
 * Copyright © 2023-2024 Rohit Parihar and Bloggios
 * All rights reserved.
 * This software is the property of Rohit Parihar and is protected by copyright law.
 * The software, including its source code, documentation, and associated files, may not be used, copied, modified, distributed, or sublicensed without the express written consent of Rohit Parihar.
 * For licensing and usage inquiries, please contact Rohit Parihar at rohitparih@gmail.com, or you can also contact support@bloggios.com.
 * This software is provided as-is, and no warranties or guarantees are made regarding its fitness for any particular purpose or compatibility with any specific technology.
 * For license information and terms of use, please refer to the accompanying LICENSE file or visit http://www.apache.org/licenses/LICENSE-2.0.
 * Unauthorized use of this software may result in legal action and liability for damages.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bloggios.user.utils;

import com.bloggios.elasticsearch.configuration.enums.FilterKeyType;
import com.bloggios.elasticsearch.configuration.payload.lspayload.Filter;
import com.bloggios.elasticsearch.configuration.payload.lspayload.SearchFilter;
import com.bloggios.elasticsearch.configuration.payload.lspayload.SearchFilterNgram;
import com.bloggios.elasticsearch.configuration.payload.lspayload.Sort;
import lombok.experimental.UtilityClass;
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - post-provider-application
 * Package - com.bloggios.user.utils
 * Created_on - 19 February-2024
 * Created_at - 15 : 23
 */

@UtilityClass
public class ListRequestUtils {

    public static Filter getFilter(List<String> selectionList, String columnName, String nestedPath, String filterKeyType, Boolean partialSearch) {
        Filter filter = Filter
                .builder()
                .filterKey(columnName)
                .nestedPath(nestedPath)
                .filterKeyType(FilterKeyType.getFilterKeyType(filterKeyType))
                .partialSearch(partialSearch)
                .build();
        List<String> selections = new ArrayList<>();
        if (Objects.nonNull(selectionList)) selectionList.stream().filter(Objects::nonNull).forEach(selections::add);
        filter.setSelections(selections);
        return filter;
    }

    public static SearchFilter getSearchFilter(String field, String nestedPath, Boolean isPartialSearch) {
        SearchFilter search = new SearchFilter();
        search.setField(field);
        search.setNestedPath(nestedPath);
        search.setPartialSearch(isPartialSearch);
        return search;
    }

    public static Sort getSort(Sort sortFilter, String columnName, String nestedPath) {
        return Sort
                .builder()
                .order(sortFilter.getOrder())
                .sortKey(columnName)
                .nestedPath(nestedPath)
                .build();
    }

    public static List<Sort> getSort(String sortKey) {
        List<Sort> defaultSort = new ArrayList<>();
        Sort lastModifiedOnSort = new Sort();
        lastModifiedOnSort.setSortKey(sortKey);
        lastModifiedOnSort.setOrder(SortOrder.DESC);
        defaultSort.add(lastModifiedOnSort);
        return defaultSort;
    }

    public static SearchFilterNgram getSearchFilterNgram(String field, String nestedPath, Boolean isPartialSearch) {
        SearchFilterNgram search = new SearchFilterNgram();
        search.setField(field);
        search.setNestedPath(nestedPath);
        search.setPartialSearch(isPartialSearch);
        return search;
    }
}
