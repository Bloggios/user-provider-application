/*
 * Copyright © 2023-2024 Rohit Parihar and Bloggios
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

package com.bloggios.user.validator.implementation.businessvalidator;

import com.bloggios.user.constants.DataErrorCodes;
import com.bloggios.user.exception.payloads.BadRequestException;
import com.bloggios.user.validator.BusinessValidator;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - user-provider-write-service
 * Package - com.bloggios.user.provider.write.validator.businessvalidatorimplementation
 * Created_on - 28 May-2024
 * Created_at - 22 : 32
 */

@Component
public class ProfileImageValidator implements BusinessValidator<MultipartFile> {

    @Override
    public void validate(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) throw new BadRequestException(DataErrorCodes.IMAGE_NOT_PRESENT);
        if (!isImageByExtension(multipartFile)) throw new BadRequestException(DataErrorCodes.NOT_IMAGE_TYPE);
        DataSize dataSize = DataSize.ofMegabytes(1);
        if (multipartFile.getSize() > dataSize.toBytes()) {
            throw new BadRequestException(DataErrorCodes.IMAGE_SIZE_LIMIT_EXCEED);
        }
    }

    private static boolean isImageByExtension(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        if (Objects.isNull(originalFilename)) {
            throw new BadRequestException(DataErrorCodes.INVALID_IMAGE_NAME);
        }
        String fileExtension = getFileExtension(originalFilename);
        return isImageExtension(fileExtension);
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private static boolean isImageExtension(String extension) {
        String[] imageExtensions = {"jpg", "jpeg", "png", "bmp"};
        for (String a : imageExtensions) {
            if (extension.equals(a)) {
                return true;
            }
        }
        return false;
    }

}
