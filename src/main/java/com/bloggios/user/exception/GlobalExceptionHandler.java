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

package com.bloggios.user.exception;

import com.bloggios.user.constants.InternalExceptionCodes;
import com.bloggios.user.constants.ServiceConstants;
import com.bloggios.user.exception.payloads.BadRequestException;
import com.bloggios.user.payload.response.ExceptionResponse;
import com.bloggios.user.properties.FetchErrorProperties;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static com.bloggios.user.constants.InternalExceptionCodes.INTERNAL_ERROR;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-provider-application
 * Package - com.bloggios.auth.provider.exception
 * Created_on - 30 November-2023
 * Created_at - 01 : 57
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final FetchErrorProperties fetchErrorProperties;

    public GlobalExceptionHandler(
            FetchErrorProperties fetchErrorProperties
    ) {
        this.fetchErrorProperties = fetchErrorProperties;
    }

    private static ExceptionResponse getUncaughtExceptionResponse(Exception exception) {
        return ExceptionResponse
                .builder()
                .code(INTERNAL_ERROR)
                .message(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .type(ServiceConstants.INTERNAL_ERROR_TYPE)
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException badRequestException) {
        ExceptionResponse exceptionResponse = fetchErrorProperties.exceptionResponse(HttpStatus.BAD_REQUEST, badRequestException.getCode());
        if (Objects.nonNull(badRequestException.getMessage())) {
            exceptionResponse = fetchErrorProperties.generateExceptionResponse(HttpStatus.BAD_REQUEST, badRequestException.getMessage(), badRequestException.getCode());
        }
        logger.error("""
                BadRequestException Occurred : {}
                Message : {}
                Field : {}
                Code : {}
                Type : {}
                """,
                MDC.get(ServiceConstants.BREADCRUMB_ID),
                exceptionResponse.getMessage(),
                exceptionResponse.getField(),
                exceptionResponse.getCode(),
                exceptionResponse.getType());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
        ExceptionResponse build = getUncaughtExceptionResponse(exception);
        logger.error("""
                Uncaught Exception Occurred : {}
                Message : {}
                Field : {}
                Code : {}
                Type : {}
                """,
                MDC.get(ServiceConstants.BREADCRUMB_ID),
                build.getMessage(),
                build.getField(),
                build.getCode(),
                build.getType());
        return new ResponseEntity<>(build, HttpStatus.valueOf(build.getStatus()));
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ExceptionResponse> handleJsonMappingException(JsonMappingException exception) {
        ExceptionResponse build = ExceptionResponse
                .builder()
                .message(exception.getMessage())
                .code(InternalExceptionCodes.JSON_DESERIALIZATION)
                .status(HttpStatus.NOT_ACCEPTABLE.name())
                .type(ServiceConstants.INTERNAL_ERROR_TYPE)
                .build();
        logger.error("""
                JSON Mapping Exception Occurred : {}
                Message : {}
                Field : {}
                Code : {}
                Type : {}
                """,
                MDC.get(ServiceConstants.BREADCRUMB_ID),
                build.getMessage(),
                build.getField(),
                build.getCode(),
                build.getType());
        return ResponseEntity
                .badRequest()
                .body(build);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNoReadableException(HttpMessageNotReadableException exception) {
        ExceptionResponse build = ExceptionResponse
                .builder()
                .message(exception.getMessage())
                .code(INTERNAL_ERROR)
                .status(HttpStatus.BAD_REQUEST.name())
                .type(ServiceConstants.INTERNAL_ERROR_TYPE)
                .build();
        logger.error("""
                Http Message Not Readable Exception Occurred : {}
                Message : {}
                Field : {}
                Code : {}
                Type : {}
                """,
                MDC.get(ServiceConstants.BREADCRUMB_ID),
                build.getMessage(),
                build.getField(),
                build.getCode(),
                build.getType());
        return ResponseEntity
                .badRequest()
                .body(build);
    }
}
