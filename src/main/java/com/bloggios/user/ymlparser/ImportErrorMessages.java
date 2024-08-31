/*
 * Copyright © 2023-2024 Bloggios
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

package com.bloggios.user.ymlparser;

import com.bloggios.user.constants.BeanConstants;
import com.bloggios.user.constants.InternalErrorCodes;
import com.bloggios.user.constants.ServiceConstants;
import com.bloggios.user.exception.payloads.InitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-provider-application
 * Package - com.bloggios.user.provider.ymlparser
 * Created_on - 11 December-2023
 * Created_at - 23 : 13
 */

@Configuration
public class ImportErrorMessages {

    @Bean(BeanConstants.ERROR_PROPERTIES_BEAN)
    public Properties errorMessages() throws IOException, InitializationException {
        File file = ResourceUtils.getFile(ServiceConstants.ERROR_MESSAGES_FILE);
        InputStream in = new FileInputStream(file);
        Properties properties = new Properties();
        try{
            properties.load(in);
        } catch(IOException exception){
            throw new InitializationException(InternalErrorCodes.FAILED_TO_FETCH_EXCEPTION_CODES);
        } finally {
            in.close();
        }
        return properties;
    }
}
