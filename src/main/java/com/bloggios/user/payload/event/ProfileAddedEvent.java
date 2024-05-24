package com.bloggios.user.payload.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.payload.event
 * Created_on - May 24 - 2024
 * Created_at - 13:55
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileAddedEvent {

    private String userId;
}
