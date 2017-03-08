package com.higgsup.smm.controller;

/**
 * Created by DangThanhLinh on 27/12/2016.
 */


import com.higgsup.smm.dto.AccessTokenDTO;
import com.higgsup.smm.dto.AccessTokenValidationResultDTO;
import com.higgsup.smm.dto.ExceptionDTO;
import com.higgsup.smm.model.entity.VerifyUser;
import com.higgsup.smm.service.impl.FacebookAccessTokenService;
import com.higgsup.smm.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/oauth/facebook/access-token")
public class FacebookAccessTokenController {

    @Autowired
    private FacebookAccessTokenService tokenService;

    @PostMapping("/exchange")
    public AccessTokenValidationResultDTO exchange(@RequestBody AccessTokenDTO accessToken) {
        return tokenService.exchange(accessToken);
    }

}

