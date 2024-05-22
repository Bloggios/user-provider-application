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

package com.bloggios.user.kafka.consumer;

import com.bloggios.user.constants.ServiceConstants;
import lombok.Getter;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerAwareMessageListener;

import java.util.Properties;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-provider-application
 * Package - com.bloggios.auth.provider.kafka.consumer
 * Created_on - 02 December-2023
 * Created_at - 21 : 23
 */

public class InitListener<A, B> {

    @Getter
    private final String[] topics;

    @Getter
    private final String groupId;

    @Getter
    private final String containerFactoryBeanName;
    private final ConsumerAwareMessageListener<A, B> messageListener;
    public static final String KAFKA_TOPIC = ServiceConstants.TOPICS;
    public static final String KAFKA_GROUP = ServiceConstants.SERVICE_NAME;
    public static final String FACTORY_NAME = ServiceConstants.SERVICE_CONTAINER_FACTORY_NAME;

    public InitListener(Properties properties, ConsumerAwareMessageListener<A, B> messageListener){
        this.topics = properties.getProperty(KAFKA_TOPIC, "").split(",");
        this.groupId = properties.getProperty(KAFKA_GROUP, "");
        this.containerFactoryBeanName = properties.getProperty(FACTORY_NAME, "");
        this.messageListener = messageListener;
    }

    @KafkaListener(
            topics = {ServiceConstants.LISTENER_TOPIC},
            groupId = ServiceConstants.LISTENER_GROUP_ID,
            containerFactory = ServiceConstants.CONTAINER_FACTORY_BEAN_NAME
    )
    public void attachListener(ConsumerRecord<A, B> consumerRecord, Consumer<?, ?> consumer){
        this.messageListener.onMessage(consumerRecord, consumer);
    }

}
