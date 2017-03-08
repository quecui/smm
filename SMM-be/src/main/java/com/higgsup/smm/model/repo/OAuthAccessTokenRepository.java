package com.higgsup.smm.model.repo;


import com.higgsup.smm.model.entity.OAuthAccessToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OAuthAccessTokenRepository extends CrudRepository<OAuthAccessToken, String> {
    OAuthAccessToken findByAccessToken(String accessToken);
    OAuthAccessToken findByUserProviderUserId(String userId);
}
