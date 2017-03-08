package com.higgsup.smm.service.impl;


import com.higgsup.smm.constant.FacebookAPIConstant;
import com.higgsup.smm.dto.AccessTokenDTO;
import com.higgsup.smm.dto.AccessTokenValidationResultDTO;
import com.higgsup.smm.dto.OAuthUserDTO;
import com.higgsup.smm.constant.FacebookAccessToken;
import com.higgsup.smm.constant.FacebookUser;
import com.higgsup.smm.model.entity.OAuthAccessToken;
import com.higgsup.smm.model.entity.OAuthUser;
import com.higgsup.smm.model.entity.Role;
import com.higgsup.smm.model.entity.VerifyUser;
import com.higgsup.smm.model.enums.OAuthProvider;
import com.higgsup.smm.model.enums.TokenValidationStatus;
import com.higgsup.smm.model.repo.OAuthAccessTokenRepository;
import com.higgsup.smm.model.repo.OAuthUserRepository;
import com.higgsup.smm.model.repo.RoleRepository;
import com.higgsup.smm.model.repo.VerifyUserRepository;
import com.higgsup.smm.service.IAccessTokenService;
import ma.glasnost.orika.MapperFacade;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class FacebookAccessTokenService implements IAccessTokenService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MapperFacade mapper;
    @Autowired
    private OAuthUserRepository oAuthUserRepository;
    @Autowired
    private OAuthAccessTokenRepository oAuthAccessTokenRepository;
    @Autowired
    private VerifyUserRepository verifyUserRepository;

    @Value("${social.facebook.app-id}")
    private String appId;

    @Value("${social.facebook.app-secret}")
    private String appSecret;

    @Override
    public AccessTokenValidationResultDTO exchange(AccessTokenDTO shortLivedToken) {

        FacebookAccessToken longLivedToken = exchangeForLongLivedToken(shortLivedToken);
        FacebookUser facebookUser = fetchUserProfile(longLivedToken.getAccessToken());

        OAuthUser user = saveUser(facebookUser);
        OAuthAccessToken accessToken = saveAccessToken(longLivedToken, user);

        return generateResponseAccessToken(accessToken, user);
    }

    private AccessTokenValidationResultDTO generateResponseAccessToken(OAuthAccessToken accessToken, OAuthUser user) {

        AccessTokenDTO responseToken = new AccessTokenDTO();
        responseToken.setToken(accessToken.getAccessToken());
        responseToken.setUser(mapper.map(user, OAuthUserDTO.class));

        AccessTokenValidationResultDTO validationResult = new AccessTokenValidationResultDTO();
        validationResult.setValidationStatus(TokenValidationStatus.VALID);
        validationResult.setAccessToken(responseToken);

        return validationResult;
    }

    private OAuthAccessToken saveAccessToken(FacebookAccessToken longLivedToken, OAuthUser user) {
        OAuthAccessToken accessToken = new OAuthAccessToken();
        accessToken.setAccessToken(longLivedToken.getAccessToken());
        Date expiredDate = DateTime.now().plusSeconds(longLivedToken.getExpiresIn()).toDate();
        accessToken.setExpiredDate(expiredDate);
        accessToken.setUser(user);
        oAuthAccessTokenRepository.save(accessToken);

        return accessToken;
    }

    private OAuthUser saveUser(FacebookUser facebookUser) {
        OAuthUser user = new OAuthUser();
        user.setProviderUserId(facebookUser.getId());
        user.setProvider(OAuthProvider.FACEBOOK);
        user.setName(facebookUser.getName());
        user.setEmail(facebookUser.getEmail());
        user.setProfileUrl(facebookUser.getLink());
        user.setAvatarUrl(facebookUser.getPicture().getData().getUrl());
        oAuthUserRepository.save(user);
        return user;
    }

    private FacebookUser fetchUserProfile(String accessToken) {

        Map<String, String> params = new HashMap<>();
        params.put("fields", "id,name,email,link,picture{url}");
        params.put("access_token", accessToken);

        return restTemplate.getForObject(
                FacebookAPIConstant.FB_USER_PROFILE_URL_TEMPLATE,
                FacebookUser.class,
                params);
    }

    private FacebookAccessToken exchangeForLongLivedToken(AccessTokenDTO shortLivedToken) {

        Map<String, String> params = new HashMap<>();
        params.put("client_id", appId);
        params.put("client_secret", appSecret);
        params.put("fb_exchange_token", shortLivedToken.getToken());

        return restTemplate.getForObject(
                FacebookAPIConstant.FB_EXCHANGE_TOKEN_URL_TEMPLATE,
                FacebookAccessToken.class,
                params);
    }

    public String toLogout(String accessToken) {
        OAuthAccessToken oAuthAccessToken = oAuthAccessTokenRepository.findByAccessToken(accessToken);
        oAuthAccessTokenRepository.delete(oAuthAccessToken);
        return "Logout done";
    }
}
