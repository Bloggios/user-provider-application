package com.bloggios.user.dao.implementation.pgsqlimplementation;

import com.bloggios.user.dao.PgAbstractDao;
import com.bloggios.user.dao.repository.postgres.ProfileEntityRepository;
import com.bloggios.user.modal.ProfileEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.dao.implementation.pgsqlimplementation
 * Created_on - May 24 - 2024
 * Created_at - 12:10
 */

@Component
public class ProfileEntityDao extends PgAbstractDao<ProfileEntity, ProfileEntityRepository> {

    protected ProfileEntityDao(ProfileEntityRepository repository) {
        super(repository);
    }

    public Optional<ProfileEntity> findByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    public Optional<ProfileEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
