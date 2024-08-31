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

package com.bloggios.user.kafka.producer;

import com.bloggios.user.payload.OutgoingMessageData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-provider-application
 * Package - com.bloggios.user.provider.kafka.producer
 * Created_on - 03 December-2023
 * Created_at - 00 : 26
 */

@Component
public abstract class MessageProducer<A> {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    private KafkaTemplate<String, PublishingPayload> kafkaTemplate;

    public void sendMessage(A a) {
        if (!setTopic().isEmpty()) {
            PublishingPayload publishingData = getPublishingData(a, UUID.randomUUID().toString());
            logger.warn("Adding Message to Queue for : {}", a.getClass().getName());
            kafkaTemplate.send(setTopic(), publishingData);
        }
    }

    public abstract String setTopic();

    @SneakyThrows(value = JsonProcessingException.class)
    private PublishingPayload getPublishingData(Object data, String breadcrumbId) {
        ObjectMapper objectMapper = new ObjectMapper();
        OutgoingMessageData build = OutgoingMessageData
                .builder()
                .breadcrumbId(breadcrumbId)
                .data(objectMapper.writeValueAsString(data))
                .build();
        return PublishingPayload.builder().messageData(objectMapper.writeValueAsString(build)).build();
    }
}
