/*
 * Copyright © 2023-2024 Bloggios
 * All rights reserved.
 * This software is the property of Rohit Parihar and is protected by copyright law.
 * The software, including its source code, documentation, and associated files, may not be used, copied, modified, distributed, or sublicensed without the express written consent of Rohit Parihar.
 * For licensing and usage inquiries, please contact Rohit Parihar at rohitparih@gmail.com, or you can also contact support@bloggios.com.
 * This software is provided as-is, and no warranties or guarantees are made regarding its fitness for any particular purpose or compatibility with any specific technology.
 * For license information and terms of use, please refer to the accompanying LICENSE file or visit http://www.apache.org/licenses/LICENSE-2.0.
 * Unauthorized use of this software may result in legal action and liability for damages.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bloggios.user.utils;

import com.bloggios.user.constants.DataErrorCodes;
import com.bloggios.user.exception.payloads.BadRequestException;
import lombok.experimental.UtilityClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public final class ValueCheckerUtil {

    public static boolean isValuePresent(String value) {
        boolean result = false;
        if (value != null && !value.isEmpty()) {
            result = true;
        }
        return result;
    }

    public static boolean isValueBoolean(String value) {
        boolean result = false;
        try {
            Pattern pattern = Pattern.compile("true|false", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(value);
            result = matcher.matches();
        } catch (IllegalArgumentException e) {
            result = false;
        }
        return result;
    }

    public static boolean isValueInteger(String value) {
        boolean result = true;
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            result = false;
        }
        return result;
    }

    public static boolean isValueDouble(String value) {
        boolean result = true;
        try {
            Double.parseDouble(value);
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    public static boolean isValueLong(String value) {
        boolean result = true;
        try {
            Long.parseLong(value);
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    public static Date parseDateFormatter(String dateValue) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
        return dateFormat.parse(dateValue);
    }

    public static <T,R> boolean isValuesPresent(T value1, R value2) {
        return value1 != null && value2 != null;
    }

    public static <T,R> boolean isValuesPartialNull(T value1,R value2) {
        return value1 != null && value2 == null;
    }

    public static void isValidUUID(String uuid) {
        try {
            UUID response = UUID.fromString(uuid);
        } catch (IllegalArgumentException ignored) {
            throw new BadRequestException(DataErrorCodes.INVALID_UUID);
        }
    }

    public static void isValidUUID(String uuid, String exceptionCode) {
        try {
            UUID response = UUID.fromString(uuid);
        } catch (IllegalArgumentException ignored) {
            throw new BadRequestException(exceptionCode);
        }
    }
}
