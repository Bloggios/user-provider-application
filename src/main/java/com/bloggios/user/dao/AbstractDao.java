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

package com.bloggios.user.dao;

import com.bloggios.user.enums.DaoStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-provider-application
 * Package - com.bloggios.auth.provider.dao
 * Created_on - 29 November-2023
 * Created_at - 23 : 55
 */

public abstract class AbstractDao<A, B extends JpaRepository<A, String>> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractDao.class);

    protected final B repository;

    protected AbstractDao(
            B repository
    ) {
        this.repository = repository;
    }

    public final A initOperation(DaoStatus status, A a) {
        return switch (status) {
            case CREATE -> initCreate(a);
            case UPDATE -> initUpdate(a);
        };
    }

    protected A initUpdate(A a) {
        logger.info("Postgres Init Operation (Update) @{}", new Date());
        return repository.save(a);
    }

    protected A initCreate(A a) {
        logger.info("Postgres Init Operation (Create) @{}", new Date());
        return repository.save(a);
    }
}
