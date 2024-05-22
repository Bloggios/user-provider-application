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

package com.bloggios.user.utils;

import com.bloggios.user.constants.InternalExceptionCodes;
import com.bloggios.user.payload.IncomingMessageData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdConverter;
import com.thoughtworks.xstream.InitializationException;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-provider-application
 * Package - com.bloggios.auth.provider.utils
 * Created_on - 02 December-2023
 * Created_at - 14 : 44
 */

public class MessageDataDeserializer extends StdConverter<String, IncomingMessageData> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public IncomingMessageData convert(String s) {
        try {
            IncomingMessageData messageData = mapper.readValue(s, IncomingMessageData.class);
            messageData.setData(mapper.readValue(messageData.getData().toString(), Object.class));
            return messageData;
        } catch (JsonProcessingException ignored) {
            throw new InitializationException(InternalExceptionCodes.JSON_DESERIALIZATION);
        }
    }
}
