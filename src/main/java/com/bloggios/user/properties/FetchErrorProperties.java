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

package com.bloggios.user.properties;

import com.bloggios.user.payload.response.ExceptionResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import java.util.Properties;

import static com.bloggios.user.constants.BeanConstants.ERROR_PROPERTIES_BEAN;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-provider-application
 * Package - com.bloggios.auth.provider.properties
 * Created_on - 11 December-2023
 * Created_at - 23 : 20
 */

@Configuration
public class FetchErrorProperties {

    private final Properties properties;

    public FetchErrorProperties(
            @Qualifier(ERROR_PROPERTIES_BEAN) Properties properties
    ) {
        this.properties = properties;
    }

    public ExceptionResponse exceptionResponse(HttpStatus status, String code) {
        String property = properties.getProperty(code);
        String message = "";
        String field = "";
        String type = "INTERNAL ERROR";
        if (!ObjectUtils.isEmpty(property)) {
            String[] strings = property.split("~");
            message = strings[0];
            field = strings.length > 1 ? strings[1] : "";
        }
        if (code.startsWith("DE")) {
            type = "DATA ERROR";
        }
        return new ExceptionResponse(message, code, field, type, status.name());
    }

    public ExceptionResponse generateExceptionResponse(HttpStatus status, String message, String code) {
        String[] split = message.split("~");
        String field = split.length > 1 ? split[1] : "";
        String type = "INTERNAL ERROR";
        if (code.startsWith("DE")) {
            type = "DATA ERROR";
        }
        return ExceptionResponse
                .builder()
                .message(split[0])
                .field(field)
                .status(status.name())
                .type(type)
                .code(code)
                .build();
    }
}
