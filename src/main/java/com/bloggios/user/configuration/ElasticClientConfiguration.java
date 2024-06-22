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

package com.bloggios.user.configuration;

import com.bloggios.user.constants.EnvironmentConstants;
import lombok.SneakyThrows;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpHeaders;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - auth-provider-application
 * Package - com.bloggios.auth.provider.configuration
 * Created_on - 02 December-2023
 * Created_at - 13 : 26
 */

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.bloggios.user.dao.repository.elasticsearch")
@ComponentScan(basePackages = "com.bloggios.user")
public class ElasticClientConfiguration extends AbstractElasticsearchConfiguration {

    private final Environment environment;

    public ElasticClientConfiguration(
            Environment environment
    ) {
        this.environment = environment;
    }

    @Override
    @Bean(destroyMethod = "close")
    @SneakyThrows
    public RestHighLevelClient elasticsearchClient() {
        String server = environment.getProperty(EnvironmentConstants.ES_SERVER);
        String username = environment.getProperty(EnvironmentConstants.ES_USERNAME);
        String password = environment.getProperty(EnvironmentConstants.ES_PASSWORD);
        HttpHeaders compatibilityHeaders = new HttpHeaders();
        compatibilityHeaders.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
        compatibilityHeaders.add("Content-Type", "application/vnd.elasticsearch+json;"
                + "compatible-with=7");
        ClientConfiguration clientConfiguration = ClientConfiguration
                .builder()
                .connectedTo(server.split(","))
                .usingSsl(sslContext())
                .withBasicAuth(username, password)
                .withDefaultHeaders(compatibilityHeaders)
                .build();
        return RestClients
                .create(clientConfiguration)
                .rest();
    }

    @Bean
    public ElasticsearchOperations template() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

    @Bean
    public SSLContext sslContext() throws Exception {
        Resource certificateResource = new ClassPathResource("certificates/http_ca.crt");
        InputStream certificateInputStream = certificateResource.getInputStream();
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setCertificateEntry("alias", loadCertificate(certificateInputStream));
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
        return sslContext;
    }

    public static Certificate loadCertificate(InputStream certificateInputStream) throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        return certificateFactory.generateCertificate(certificateInputStream);
    }
}
