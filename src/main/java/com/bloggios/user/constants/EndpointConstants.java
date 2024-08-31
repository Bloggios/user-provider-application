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
 * Created_on - 28 May-2024
 * Created_at - 20 : 34
 */

@UtilityClass
public class EndpointConstants {

    public static class ProfileController {
        public static final String BASE_PATH = "/profile";
        public static final String ADD_PROFILE_IMAGE = "/profile-image";
    }

    public static class FeignClient {
        public static final String USER_PROFILE_RESPONSE = "/auth-provider/user-profile-response";
        public static final String BLOG_COUNT_INTERNAL_RESPONSE = "/blog-provider/unauth/count-blogs";
    }

    public static class ProfileAuthController {
        public static final String BASE_PATH = "/profile-auth";
        public static final String PROFILE_TAGS = "/profile-tags";
        public static final String USER_PROFILE = "/user-profile";
    }

    public static class FollowController {
        public static final String BASE_PATH = "/follow";
        public static final String HANDLE_FOLLOW = "/handle-follow/{userId}";
        public static final String COUNT_FOLLOW = "/count";
    }

    public static class OpenController {
        public static final String BASE_PATH = "/unauth";
        public static final String PROFILE_INTERNAL_RESPONSE = "/profile-internal-response/{userId}";

        public static class Profile {
            public static final String USER_PROFILE = "/user-profile";
            public static final String FETCH_PROFILES_USERNAME = "/get-username-profiles-list";
        }
    }
}
