package com.bloggios.user.validator.implementation.businessvalidator;

import com.bloggios.user.constants.DataErrorCodes;
import com.bloggios.user.constants.ServiceConstants;
import com.bloggios.user.exception.payloads.BadRequestException;
import com.bloggios.user.validator.BusinessValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.regex.Pattern;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.validator.implementation.businessvalidator
 * Created_on - May 23 - 2024
 * Created_at - 21:33
 */

@Component
public class NameValidator implements BusinessValidator<String> {

    @Override
    public void validate(String name) {
        if (ObjectUtils.isEmpty(name)) throw new BadRequestException(DataErrorCodes.NAME_MANDATORY);
        if (name.length() > 40) throw new BadRequestException(DataErrorCodes.NAME_LENGTH_EXCEEDED);
        Pattern pattern = Pattern.compile(ServiceConstants.NAME_REGEX);
        if (!pattern.matcher(name).matches()) {
            throw new BadRequestException(DataErrorCodes.NAME_INVALID);
        }
    }
}
