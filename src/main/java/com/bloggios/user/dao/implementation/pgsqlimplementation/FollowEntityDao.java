package com.bloggios.user.dao.implementation.pgsqlimplementation;

import com.bloggios.user.dao.PgAbstractDao;
import com.bloggios.user.dao.repository.postgres.FollowEntityRepository;
import com.bloggios.user.modal.FollowEntity;
import com.bloggios.user.modal.ProfileEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Owner - Rohit Parihar
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.provider.dao.implementation.pgsql
 * Created_on - 29 January-2024
 * Created_at - 19 : 17
 */

@Component
public class FollowEntityDao extends PgAbstractDao<FollowEntity, FollowEntityRepository> {

    protected FollowEntityDao(FollowEntityRepository repository) {
        super(repository);
    }

    public Optional<FollowEntity> findByFollowerAndFollowing(ProfileEntity followedBy, ProfileEntity followTo) {
        return repository.findByFollowedByAndFollowTo(followedBy, followTo);
    }

    public void deleteByEntity(FollowEntity followEntity) {
        repository.delete(followEntity);
    }
}
