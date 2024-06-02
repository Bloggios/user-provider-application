package com.bloggios.user.modal;

import com.bloggios.user.constants.ServiceConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.modal
 * Created_on - 29 January-2024
 * Created_at - 19 : 01
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "follow")
public class FollowEntity {

    @Id
    @GeneratedValue(generator = ServiceConstants.RANDOM_UUID)
    @GenericGenerator(name = ServiceConstants.RANDOM_UUID, strategy = "org.hibernate.id.UUIDGenerator")
    private String followId;

    @Column(nullable = false)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "follow_to")
    private ProfileEntity followTo;

    @ManyToOne
    @JoinColumn(name = "followed_by")
    private ProfileEntity followedBy;

    @Column(nullable = false)
    private String version;

    private String apiVersion;

    @Temporal(TemporalType.TIMESTAMP)
    private Date followedOn;
}
