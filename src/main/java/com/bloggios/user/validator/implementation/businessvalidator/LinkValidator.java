package com.bloggios.user.validator.implementation.businessvalidator;

import com.bloggios.user.constants.DataErrorCodes;
import com.bloggios.user.constants.ServiceConstants;
import com.bloggios.user.exception.payloads.BadRequestException;
import com.bloggios.user.validator.BusinessValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.validator.implementation.businessvalidator
 * Created_on - May 24 - 2024
 * Created_at - 11:59
 */

@Component
public class LinkValidator implements BusinessValidator<String> {

    @Override
    public void validate(String link) {
        if (StringUtils.hasText(link)) {
            if (link.startsWith(ServiceConstants.UNSECURED_PROTOCOL))
                throw new BadRequestException(DataErrorCodes.UNSECURED_PROTOCOL);
            if (!link.startsWith(ServiceConstants.SECURED_PROTOCOL))
                throw new BadRequestException(DataErrorCodes.LINK_PROTOCOL_NOT_PRESENT);
        }
    }
}
