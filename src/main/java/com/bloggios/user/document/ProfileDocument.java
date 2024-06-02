package com.bloggios.user.document;

import com.bloggios.user.constants.EnvironmentConstants;
import com.bloggios.user.constants.ServiceConstants;
import com.bloggios.user.enums.ProfileTag;
import com.bloggios.user.enums.UserBadge;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.document
 * Created_on - May 23 - 2024
 * Created_at - 12:28
 */

@Document(
        indexName = EnvironmentConstants.PROFILE_INDEX_GET_PROPERTY
)
@Setting(
        settingPath = EnvironmentConstants.ES_SETTING
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileDocument {

    @Id
    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String profileId;

    @MultiField(
            mainField = @Field(
                    type = FieldType.Text,
                    analyzer = ServiceConstants.DEFAULT_AUTOCOMPLETE,
                    fielddata = true
            ),
            otherFields = {
                    @InnerField(
                            suffix = ServiceConstants.VERBATIM,
                            type = FieldType.Keyword,
                            normalizer = ServiceConstants.DEFAULT_NORMALIZER
                    )
            }
    )
    private String name;

    @MultiField(
            mainField = @Field(
                    type = FieldType.Text,
                    analyzer = ServiceConstants.DEFAULT_AUTOCOMPLETE,
                    fielddata = true
            ),
            otherFields = {
                    @InnerField(
                            suffix = ServiceConstants.VERBATIM,
                            type = FieldType.Keyword,
                            normalizer = ServiceConstants.DEFAULT_NORMALIZER
                    )
            }
    )
    private String email;

    @MultiField(
            mainField = @Field(
                    type = FieldType.Text,
                    analyzer = ServiceConstants.DEFAULT_AUTOCOMPLETE,
                    fielddata = true
            ),
            otherFields = {
                    @InnerField(
                            suffix = ServiceConstants.VERBATIM,
                            type = FieldType.Keyword,
                            normalizer = ServiceConstants.DEFAULT_NORMALIZER
                    )
            }
    )
    private String username;

    @Field(
            type = FieldType.Text
    )
    private String bio;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String link;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String userId;

    @Field(
            type = FieldType.Date
    )
    private Date createdOn;

    @Field(
            type = FieldType.Date
    )
    private Date updatedOn;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String version;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String apiVersion;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private String profileImage;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private ProfileTag profileTag;

    @Field(
            type = FieldType.Keyword,
            normalizer = ServiceConstants.DEFAULT_NORMALIZER
    )
    private UserBadge userBadge;

    @Field(
            type = FieldType.Boolean
    )
    private boolean isBadge;
}
