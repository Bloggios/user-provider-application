package com.bloggios.user.validator.implementation.businessvalidator;

import com.bloggios.user.constants.DataErrorCodes;
import com.bloggios.user.exception.payloads.BadRequestException;
import com.bloggios.user.utils.WordsCounter;
import com.bloggios.user.validator.BusinessValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.validator.implementation.businessvalidator
 * Created_on - May 23 - 2024
 * Created_at - 21:42
 */

@Component
public class BioValidator implements BusinessValidator<String> {

    @Override
    public void validate(String bio) {
        if (StringUtils.hasText(bio)) {
            if (bio.length() > 150) throw new BadRequestException(DataErrorCodes.BIO_LIMIT_EXCEED);
            int words = WordsCounter.countWords(bio);
            if (words > 25) throw new BadRequestException(DataErrorCodes.BIO_WORD_LIMIT_EXCEED);
            if (countNewLines(bio) >= 3) throw new BadRequestException(DataErrorCodes.BIO_LINE_LIMIT_EXCEED);
        }
    }

    private static int countNewLines(String data) {
        int count = 0;
        int index = data.indexOf('\n');
        while(index != -1) {
            count++;
            index = data.indexOf('\n', index + 1);
        }
        return count;
    }
}
