package com.bloggios.user.enums;

import com.bloggios.user.constants.DataErrorCodes;
import com.bloggios.user.exception.payloads.BadRequestException;
import lombok.Getter;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.enums
 * Created_on - 23 February-2024
 * Created_at - 12 : 47
 */

@Getter
public enum ProfileTag {

    OTHER("Other"),
    SOFTWARE("Software Developer"),
    FREELANCER("Freelancer"),
    INFLUENCER("Influencer"),
    ARTIST("Artist"),
    ACTOR("Actor"),
    BLOG("Personal Blog"),
    COMMUNITY("Community"),
    EDUCATION("Education"),
    STUDENT("Student"),
    ENTREPRENEUR("Entrepreneur"),
    GAMER("Gamer"),
    PHOTOGRAPHER("Photographer"),
    MUSICIAN("Musician"),
    COMPANY("Company");

    private final String value;

    ProfileTag(String value) {
        this.value = value;
    }

    public static ProfileTag getByValue(String value) {
        for (ProfileTag profileTag : values()) {
            if (profileTag.value.equals(value)) {
                return profileTag;
            }
        }
        throw new BadRequestException(DataErrorCodes.INVALID_PROFILE_TAG_VALUE);
    }
}
