package com.bloggios.user.transformer.implementation;

import com.bloggios.user.document.ProfileDocument;
import com.bloggios.user.payload.response.ProfileResponse;
import com.bloggios.user.transformer.Transform;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.transformer.implementation
 * Created_on - May 28 - 2024
 * Created_at - 16:42
 */

@Component
public class ProfileDocumentToProfileResponseTransformer implements Transform<ProfileResponse, ProfileDocument> {

    private final ModelMapper modelMapper;

    public ProfileDocumentToProfileResponseTransformer(
            ModelMapper modelMapper
    ) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfileResponse transform(ProfileDocument profileDocument) {
        return modelMapper.map(profileDocument, ProfileResponse.class);
    }
}
