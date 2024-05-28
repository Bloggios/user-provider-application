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

package com.bloggios.user.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - user-provider-write-service
 * Package - com.bloggios.user.provider.write.file
 * Created_on - 26 May-2024
 * Created_at - 19 : 17
 */

@Component
public class DeleteFile {

    private static final Logger logger = LoggerFactory.getLogger(DeleteFile.class);

    public void deleteImage(String path, String imageName) {
        String imagePath = path + File.separator + imageName;
        File file = new File(imagePath);
        if (file.exists()) {
            boolean delete = file.delete();
            logger.warn("""
                    File Deleted : {}
                    File Name : {}
                    Complete Path : {},
                    """, delete, imageName, imagePath);
        } else {
            logger.error("""
                    File Not found with name : {},
                    Complete Path : {}
                    Deletion Failed
                    """, imageName, imagePath);
        }
    }
}
