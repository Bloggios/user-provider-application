package com.bloggios.user.dao.repository.postgres;

import com.bloggios.user.modal.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.dao.repository.postgres
 * Created_on - May 24 - 2024
 * Created_at - 12:08
 */

public interface ProfileEntityRepository extends JpaRepository<ProfileEntity, String> {

    Optional<ProfileEntity> findByUserId(String userId);
}
