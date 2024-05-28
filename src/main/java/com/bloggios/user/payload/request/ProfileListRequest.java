package com.bloggios.user.payload.request;

import com.bloggios.elasticsearch.configuration.payload.lspayload.Filter;
import com.bloggios.elasticsearch.configuration.payload.lspayload.Sort;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.payload.request
 * Created_on - May 27 - 2024
 * Created_at - 12:27
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileListRequest {

    private List<Sort> sorts;
    private List<Filter> filters;
    private List<String> texts;
    private Integer page;
    private Integer size;
}
