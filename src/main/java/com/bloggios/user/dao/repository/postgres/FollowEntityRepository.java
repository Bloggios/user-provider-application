package com.bloggios.user.dao.repository.postgres;

import com.bloggios.user.modal.FollowEntity;
import com.bloggios.user.modal.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.dao.repository.pgsqlRepository
 * Created_on - 29 January-2024
 * Created_at - 19 : 12
 */

public interface FollowEntityRepository extends JpaRepository<FollowEntity, String> {

    Optional<FollowEntity> findByFollowedByAndFollowTo(ProfileEntity followedBy, ProfileEntity followTo);
}
