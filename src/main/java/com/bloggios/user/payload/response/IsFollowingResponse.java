package com.bloggios.user.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.payload.response
 * Created_on - 04 February-2024
 * Created_at - 12 : 24
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class IsFollowingResponse {

    private Boolean isFollowing;
    private String userId;
}
