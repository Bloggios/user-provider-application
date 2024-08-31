package com.bloggios.user.configuration;

import com.bloggios.user.properties.SwaggerConfigProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.user.configuration
 * Created_on - August 30 - 2024
 * Created_at - 01:19
 */

@Configuration
@RequiredArgsConstructor
public class SwaggerConfiguration {

    private final SwaggerConfigProperties swaggerConfigProperties;

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group(swaggerConfigProperties.getGroupName().getDefinition())
                .packagesToScan(swaggerConfigProperties.getGroupName().getScanPackages())
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(swaggerConfigProperties.getInfo().getTitle())
                        .version(swaggerConfigProperties.getInfo().getVersion())
                        .description(swaggerConfigProperties.getInfo().getDescription())
                        .license(new License().name(swaggerConfigProperties.getInfo().getLicense().getName()).url(swaggerConfigProperties.getInfo().getLicense().getUrl()))
                        .contact(new Contact().name(swaggerConfigProperties.getInfo().getContact().getName()).email(swaggerConfigProperties.getInfo().getContact().getEmail()).url(swaggerConfigProperties.getInfo().getContact().getUrl())))
                .servers(getServers())
                .components(new Components()
                        .addSecuritySchemes(
                                "bearerAuth",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("JWT Authentication")));
    }

    public List<Server> getServers(){
        Map<String, SwaggerConfigProperties.Server> servers = swaggerConfigProperties.getServers();
        List<Server> serversList = new ArrayList<>();
        for (String server : servers.keySet()){
            SwaggerConfigProperties.Server getServer = servers.get(server);
            serversList.add(new Server().description(getServer.getName()).url(getServer.getUrl()));
        }
        return serversList;
    }
}
