package com.bloggios.user.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.payload.request
 * Created_on - May 23 - 2024
 * Created_at - 13:15
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileRequest {

    private String name;
    private String link;
    private String bio;
    private String profileTag;
}
