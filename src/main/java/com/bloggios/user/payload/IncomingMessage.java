package com.bloggios.user.payload;

import com.bloggios.user.utils.MessageDataDeserializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - bloggios-email-service
 * Package - com.bloggios.email.payload
 * Created_on - May 20 - 2024
 * Created_at - 12:54
 */

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IncomingMessage {

    @JsonDeserialize(converter = MessageDataDeserializer.class)
    @JsonAlias("messageData")
    private IncomingMessageData messageData;
}
