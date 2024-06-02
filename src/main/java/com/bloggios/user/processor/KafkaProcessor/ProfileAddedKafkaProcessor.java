package com.bloggios.user.processor.KafkaProcessor;

import com.bloggios.user.document.ProfileDocument;
import com.bloggios.user.kafka.producer.producers.ProfileAddedProducer;
import com.bloggios.user.payload.event.ProfileAddedEvent;
import com.bloggios.user.processor.Process;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.processor.KafkaProcessor
 * Created_on - May 24 - 2024
 * Created_at - 13:44
 */

@Component
public class ProfileAddedKafkaProcessor implements Process<ProfileDocument> {

    private final ProfileAddedProducer producer;

    public ProfileAddedKafkaProcessor(
            ProfileAddedProducer producer
    ) {
        this.producer = producer;
    }

    @Override
    public void process(ProfileDocument profileDocument) {
        producer.sendMessage(ProfileAddedEvent.builder().userId(profileDocument.getUserId()).build());
    }
}
