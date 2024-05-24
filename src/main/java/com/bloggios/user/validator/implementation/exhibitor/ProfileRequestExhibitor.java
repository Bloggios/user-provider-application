package com.bloggios.user.validator.implementation.exhibitor;

import com.bloggios.user.payload.request.ProfileRequest;
import com.bloggios.user.utils.AsyncUtils;
import com.bloggios.user.validator.Exhibitor;
import com.bloggios.user.validator.implementation.businessvalidator.BioValidator;
import com.bloggios.user.validator.implementation.businessvalidator.LinkValidator;
import com.bloggios.user.validator.implementation.businessvalidator.NameValidator;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.validator.implementation.exhibitor
 * Created_on - May 23 - 2024
 * Created_at - 21:33
 */

@Component
public class ProfileRequestExhibitor implements Exhibitor<ProfileRequest> {

    private final NameValidator nameValidator;
    private final BioValidator bioValidator;
    private final LinkValidator linkValidator;

    public ProfileRequestExhibitor(
            NameValidator nameValidator,
            BioValidator bioValidator,
            LinkValidator linkValidator
    ) {
        this.nameValidator = nameValidator;
        this.bioValidator = bioValidator;
        this.linkValidator = linkValidator;
    }

    @Override
    public void validate(ProfileRequest profileRequest) {
        CompletableFuture<Void> validateName = CompletableFuture.runAsync(() -> nameValidator.validate(profileRequest.getName()));
        CompletableFuture<Void> validateBio = CompletableFuture.runAsync(() -> bioValidator.validate(profileRequest.getBio()));
        CompletableFuture<Void> validateLink = CompletableFuture.runAsync(() -> linkValidator.validate(profileRequest.getLink()));
        AsyncUtils.getAsyncResult(CompletableFuture.allOf(
                validateName,
                validateBio,
                validateLink
        ));
    }
}
