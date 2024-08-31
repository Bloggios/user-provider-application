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

package com.bloggios.user.constants;

import lombok.experimental.UtilityClass;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-provider-application
 * Package - com.bloggios.user.provider.constants
 * Created_on - 29 November-2023
 * Created_at - 00 : 58
 */

@UtilityClass
public class EnvironmentConstants {

    public static final String APPLICATION_VERSION = "application.version";
    public static final String KAFKA_GROUP_ID = "user-provider.kafka.consumer.group-id";
    public static final String ES_SERVER = "elasticsearch.server";
    public static final String ES_USERNAME = "elasticsearch.username";
    public static final String ES_PASSWORD = "elasticsearch.password";
    public static final String ES_SETTING = "/es-setting.json";
    public static final String PROFILE_INDEX_GET_PROPERTY = "#{@environment.getProperty('elasticsearch.indices.profile')}";
    public static final String BASE_PATH = "#{@environment.getProperty('application.base-path')}";
    public static final String PROFILE_ADDED_TOPIC = "user-provider.kafka.producer.topics.profile-added";
    public static final String PROFILE_INDEX = "elasticsearch.indices.profile";
    public static final String PROFILE_IMAGE_PATH = "user-files.profile-photo";
    public static final String ACTIVE_PROFILE = "application.profile";
    public static final String ASSETS = "application.environment.assets";
    public static final String FOLLOW_INDEX_GET_PROPERTY = "#{@environment.getProperty('elasticsearch.indices.follow')}";
    public static final String FOLLOW_DOCUMENT_INDEX = "elasticsearch.indices.follow";
}
