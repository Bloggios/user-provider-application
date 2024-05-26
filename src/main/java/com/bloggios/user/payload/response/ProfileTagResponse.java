package com.bloggios.user.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.payload.response
 * Created_on - 18 March-2024
 * Created_at - 20 : 10
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileTagResponse {

    private List<String> tags;
    private int totalRecordCounts;
}
