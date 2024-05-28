package com.bloggios.user.payload.response;

import com.bloggios.user.constants.ServiceConstants;
import com.bloggios.user.enums.ProfileTag;
import com.bloggios.user.enums.UserBadge;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import java.util.Date;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.payload.response
 * Created_on - May 28 - 2024
 * Created_at - 16:32
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileResponse {

    private String profileId;
    private String name;
    private String email;
    private String username;
    private String bio;
    private String link;
    private String userId;
    private Date createdOn;
    private Date updatedOn;
    private String version;
    private String profileImage;
    private ProfileTag profileTag;
    private UserBadge userBadge;
    private boolean isBadge;
}
