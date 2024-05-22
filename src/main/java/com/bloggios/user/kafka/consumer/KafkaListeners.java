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

import com.bloggios.user.constants.EnvironmentConstants;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static com.bloggios.user.constants.BeanConstants.INCOMING_MESSAGE_FACTORY_COMPONENT;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-provider-application
 * Package - com.bloggios.auth.provider.kafka.consumer
 * Created_on - 02 December-2023
 * Created_at - 21 : 23
 */

@Component
public class KafkaListeners {

    private final Environment environment;

    public KafkaListeners(
            Environment environment
    ) {
        this.environment = environment;
    }

    private Properties getProperties(String topic) {
        Properties properties = new Properties();
        properties.put(InitListener.KAFKA_TOPIC, environment.getProperty(topic));
        properties.put(InitListener.KAFKA_GROUP, environment.getProperty(EnvironmentConstants.KAFKA_GROUP_ID));
        properties.put(InitListener.FACTORY_NAME, INCOMING_MESSAGE_FACTORY_COMPONENT);
        return properties;
    }
}
