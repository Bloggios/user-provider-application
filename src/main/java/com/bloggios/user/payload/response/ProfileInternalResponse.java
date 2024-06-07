package com.bloggios.user.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.payload.response
 * Created_on - June 07 - 2024
 * Created_at - 17:40
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileInternalResponse {

    private String name;
    private String email;
    private String profileTag;
    private String profileImage;
    private boolean isBadge;
}
