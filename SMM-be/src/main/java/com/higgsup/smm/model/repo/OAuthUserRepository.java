package com.higgsup.smm.model.repo;


import com.higgsup.smm.model.entity.OAuthUser;
import com.higgsup.smm.model.entity.PageAccessToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OAuthUserRepository extends CrudRepository<OAuthUser, String> {
    OAuthUser findByProviderUserId(String userId);
    OAuthUser findByEmail(String email);
}
