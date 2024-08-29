package com.bloggios.user.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.payload.request
 * Created_on - June 23 - 2024
 * Created_at - 14:35
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MustNotQueryListRequest {

    private String key;
    private Collection<? extends String> dataToExclude;
    private int page;
    private int size;
}
