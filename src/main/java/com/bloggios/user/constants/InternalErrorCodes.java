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
 * Created_at - 13 : 57
 */

/**
 *
 * WT -> Write API
 * DE -> User Error or Data Error
 * IE -> Internal Server Error
 *
 */

@UtilityClass
public class InternalErrorCodes {

    public static final String JSON_DESERIALIZATION = "IE__USER-1001";
    public static final String INTERNAL_ERROR = "IE__USER-1002";
    public static final String FAILED_TO_FETCH_EXCEPTION_CODES = "IE__USER-1003";
    public static final String UNABLE_TO_FETCH_USER_PROFILE_RESPONSE = "IE__USER-1004";
    public static final String FAILED_TO_DELETE_FOLLOW_ES = "IE__USER-1005";
}
