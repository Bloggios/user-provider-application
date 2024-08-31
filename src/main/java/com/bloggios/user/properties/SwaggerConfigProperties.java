package com.bloggios.user.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.user.properties
 * Created_on - August 30 - 2024
 * Created_at - 01:35
 */

@Getter
@Setter
@ConfigurationProperties(prefix = "swagger-properties")
@Configuration
public class SwaggerConfigProperties {

    private Info info;
    private Map<String, Server> servers;
    private Group groupName;


    @Getter
    @Setter
    public static class Info{
        private String title;
        private String description;
        private String summary;
        private String version;
        private Contact contact;
        private License license;
    }

    @Getter
    @Setter
    public static class Contact{
        private String name;
        private String email;
        private String url;
    }

    @Getter
    @Setter
    public static class License{
        private String name;
        private String url;
    }

    @Getter
    @Setter
    public static class Server{
        private String name;
        private String url;
    }

    @Getter
    @Setter
    public static class Group{
        private String definition;
        private String scanPackages;
    }
}
