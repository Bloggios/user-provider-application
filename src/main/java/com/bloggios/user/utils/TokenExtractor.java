package com.bloggios.user.utils;

import com.bloggios.user.constants.ServiceConstants;
import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.utils
 * Created_on - May 24 - 2024
 * Created_at - 12:56
 */

@UtilityClass
public class TokenExtractor {

    public static String extractToken(HttpServletRequest request) {
        String header = request.getHeader(ServiceConstants.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
