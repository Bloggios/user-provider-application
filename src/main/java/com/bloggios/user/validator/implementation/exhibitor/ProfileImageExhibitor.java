package com.bloggios.user.validator.implementation.exhibitor;

import com.bloggios.user.validator.Exhibitor;
import com.bloggios.user.validator.implementation.businessvalidator.ProfileImageValidator;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.validator.implementation.exhibitor
 * Created_on - May 28 - 2024
 * Created_at - 20:20
 */

@Component
public class ProfileImageExhibitor implements Exhibitor<MultipartFile> {

    private final ProfileImageValidator profileImageValidator;

    public ProfileImageExhibitor(
            ProfileImageValidator profileImageValidator
    ) {
        this.profileImageValidator = profileImageValidator;
    }

    @Override
    public void validate(MultipartFile multipartFile) {
        profileImageValidator.validate(multipartFile);
    }
}
