package com.bloggios.user.exception.payloads;

import com.bloggios.user.exception.ExceptionProvider;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.exception.payloads
 * Created_on - May 24 - 2024
 * Created_at - 13:09
 */

@Getter
@EqualsAndHashCode(callSuper = true)
public class InternalException extends ExceptionProvider {

    private String message;

    public InternalException(String code) {
        super(code);
    }

    public InternalException(String code, String message) {
        super(code);
        this.message = message;
    }
}
