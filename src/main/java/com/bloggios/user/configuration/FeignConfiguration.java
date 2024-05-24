package com.bloggios.user.configuration;

import com.bloggios.user.feign.FeignException;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.configuration
 * Created_on - May 24 - 2024
 * Created_at - 13:03
 */

@Configuration
public class FeignConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignException();
    }
}
