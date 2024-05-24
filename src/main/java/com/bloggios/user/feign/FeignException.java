package com.bloggios.user.feign;

import com.bloggios.authenticationconfig.exception.AuthenticationConfigException;
import com.bloggios.user.exception.payloads.BadRequestException;
import com.bloggios.user.exception.payloads.InternalException;
import com.bloggios.user.payload.response.ExceptionResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.feign
 * Created_on - May 24 - 2024
 * Created_at - 13:00
 */

public class FeignException implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionResponse exceptionResponse = (ExceptionResponse) response.body();
        return switch (response.status()) {
            case 400 -> new BadRequestException(exceptionResponse.getCode(), exceptionResponse.getMessage());
            case 500 -> new InternalException(exceptionResponse.getCode(), exceptionResponse.getMessage());
            case 401, 403 -> new AuthenticationConfigException(exceptionResponse.getMessage());
            default -> errorDecoder.decode(methodKey, response);
        };
    }
}
