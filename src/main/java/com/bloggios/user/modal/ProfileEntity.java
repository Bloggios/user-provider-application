package com.bloggios.user.modal;

import com.bloggios.user.constants.ServiceConstants;
import com.bloggios.user.enums.ProfileTag;
import com.bloggios.user.enums.UserBadge;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.modal
 * Created_on - May 22 - 2024
 * Created_at - 22:20
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = ServiceConstants.PROFILE_TABLE)
public class ProfileEntity {

    @Id
    @GeneratedValue(generator = ServiceConstants.RANDOM_UUID)
    @GenericGenerator(name = ServiceConstants.RANDOM_UUID, strategy = "org.hibernate.id.UUIDGenerator")
    private String profileId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    @Column(length = 5000)
    private String bio;

    @Column(length = 5000)
    private String link;

    @Column(nullable = false, unique = true)
    private String userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Column(nullable = false)
    private String version;

    private String apiVersion;

    @Column(length = 1000)
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private ProfileTag profileTag;

    @Enumerated(EnumType.STRING)
    private UserBadge userBadge;

    private boolean isBadge;
}
