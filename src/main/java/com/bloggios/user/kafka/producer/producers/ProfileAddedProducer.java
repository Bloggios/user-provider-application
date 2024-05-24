package com.bloggios.user.kafka.producer.producers;

import com.bloggios.user.constants.EnvironmentConstants;
import com.bloggios.user.kafka.producer.MessageProducer;
import com.bloggios.user.payload.event.ProfileAddedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.kafka.producer.producers
 * Created_on - May 24 - 2024
 * Created_at - 13:54
 */

@Component
public class ProfileAddedProducer extends MessageProducer<ProfileAddedEvent> {

    private final Environment environment;

    public ProfileAddedProducer(
            Environment environment
    ) {
        this.environment = environment;
    }

    @Override
    public String setTopic() {
        return Objects.requireNonNull(environment.getProperty(EnvironmentConstants.PROFILE_ADDED_TOPIC));
    }
}
