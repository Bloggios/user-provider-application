package com.bloggios.user.constants;

import lombok.experimental.UtilityClass;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.constants
 * Created_on - May 22 - 2024
 * Created_at - 16:50
 */

@UtilityClass
public class DataErrorCodes {
    public static final String INVALID_UUID = "DE__USER-2001";
    public static final String INVALID_PROFILE_TAG_VALUE = "DE__USER-2002";
    public static final String NAME_MANDATORY = "DE__USER-2003";
    public static final String NAME_LENGTH_EXCEEDED = "DE__USER-2004";
    public static final String NAME_INVALID = "DE__USER-2005";
    public static final String BIO_LIMIT_EXCEED = "DE__USER-2006";
    public static final String BIO_WORD_LIMIT_EXCEED = "DE__USER-2007";
    public static final String BIO_LINE_LIMIT_EXCEED = "DE__USER-2008";
    public static final String UNSECURED_PROTOCOL = "DE__USER-2009";
    public static final String LINK_PROTOCOL_NOT_PRESENT = "DE__USER-2010";
    public static final String PROFILE_ALREADY_PRESENT = "DE__USER-2011";
    public static final String USER_LIST_REQUEST_NULL = "DE__USER-2012";
    public static final String FILTER_KEY_NOT_VALID = "DE__USER-2014";
    public static final String SORT_KEY_NOT_VALID = "DE__USER-2015";
    public static final String PROFILE_NOT_FOUND = "DE__USER-2016";
    public static final String IMAGE_NOT_PRESENT = "DE__USER-2017";
    public static final String NOT_IMAGE_TYPE = "DE__USER-2018";
    public static final String IMAGE_SIZE_LIMIT_EXCEED = "DE__USER-2019";
    public static final String INVALID_IMAGE_NAME = "DE__USER-2020";
    public static final String INVALID_USER_ID = "DE__USER-2021";
    public static final String USER_CANNOT_FOLLOW_ITSELF = "DE__USER-2022";
    public static final String USER_NOT_FOUND = "DE__USER-2023";
    public static final String FOLLOW_TO_USER_NOT_FOUND = "DE__USER-2024";
    public static final String USER_ALREADY_FOLLOWING_THAT_USER = "DE__USER-2025";
    public static final String USER_NOT_FOLLOWING_EACH_OTHER = "DE__USER-2026";
}
