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

package com.bloggios.user.service;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.elasticsearch.configuration.payload.response.ListResponse;
import com.bloggios.user.payload.request.ProfileListRequest;
import com.bloggios.user.payload.request.ProfileRequest;
import com.bloggios.user.payload.response.ModuleResponse;
import com.bloggios.user.payload.response.ProfileInternalResponse;
import com.bloggios.user.payload.response.ProfileResponse;
import com.bloggios.user.payload.response.ProfileTagResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-provider-write-service
 * Package - com.bloggios.user.provider.write.service
 * Created_on - 30 December-2023
 * Created_at - 16 : 23
 */

public interface ProfileService {

    CompletableFuture<ModuleResponse> addProfile(ProfileRequest profileRequest, AuthenticatedUser authenticatedUser, HttpServletRequest httpServletRequest);
    CompletableFuture<ModuleResponse> profileImage(MultipartFile multipartFile, AuthenticatedUser authenticatedUser);
    CompletableFuture<ModuleResponse> updateProfile(ProfileRequest profileRequest, AuthenticatedUser authenticatedUser);
    CompletableFuture<ProfileInternalResponse> getProfileInternalResponse(String userId);
    CompletableFuture<ProfileResponse> getUserProfile(String email, AuthenticatedUser authenticatedUser);
    CompletableFuture<ProfileTagResponse> getProfileTags();
    CompletableFuture<ListResponse> getProfileList(ProfileListRequest profileListRequest);
    CompletableFuture<ProfileResponse> getMyProfile(AuthenticatedUser authenticatedUser);
    CompletableFuture<ListResponse> fetchProfilesUsingUsername(String username);
}
