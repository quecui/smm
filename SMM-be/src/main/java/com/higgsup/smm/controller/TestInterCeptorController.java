package com.higgsup.smm.controller;

import com.higgsup.smm.constant.Constant;
import com.higgsup.smm.interceptor.RoleAdmin;
import com.higgsup.smm.interceptor.NoAuthenticationCheck;;
import com.higgsup.smm.service.impl.FacebookAccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by DangThanhLinh on 30/12/2016.
 */
@RestController
public class TestInterCeptorController {
    @Autowired
    private FacebookAccessTokenService tokenService;

    @NoAuthenticationCheck
    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @RoleAdmin
    @RequestMapping(value = "/ADMIN", method = RequestMethod.GET)
    public String testRoleADMIN() {
        return "THIS IS RoleAdmin";
    }

    @RequestMapping(value = "/MODERATOR", method = RequestMethod.GET)
    public String testRoleMODERATOR() {
        return "THIS IS RoleModerator";
    }

    @RequestMapping(value = "/loguot", method = RequestMethod.POST)
    public String logout(HttpServletRequest request) {
        String token = request.getHeader(Constant.AUTH_TOKEN_HEADER);
        return tokenService.toLogout(token);
    }
}
