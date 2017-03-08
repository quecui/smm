package com.higgsup.smm.controller;

import com.higgsup.smm.constant.Constant;
import com.higgsup.smm.dto.InviteUserDTO;
import com.higgsup.smm.dto.OAuthUserDTO;
import com.higgsup.smm.dto.UserDTO;
import com.higgsup.smm.interceptor.NoAuthenticationCheck;
import com.higgsup.smm.interceptor.RoleAdmin;
import com.higgsup.smm.service.impl.UserService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by StormSpirit on 1/10/2017.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

  //  @NoAuthenticationCheck
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<UserDTO> getAll(HttpServletRequest request){
        String tokenUser = request.getHeader(Constant.AUTH_TOKEN_HEADER);
        String pageAccessToken = request.getHeader(Constant.PAGE_TOKEN_HEADER);
        return userService.getAllUsers(tokenUser, pageAccessToken);
    }
//    Role admin
  //  @RoleAdmin
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String inviteUser(@RequestBody InviteUserDTO inviteUserDTO) throws TemplateException, IOException, MessagingException {
        return userService.inviteUser(inviteUserDTO);
    }

   // @NoAuthenticationCheck
    @RequestMapping(value = "/verify/{userId}/{token}", method = RequestMethod.POST)
    public String verifyUser(@PathVariable("userId") String userId, @PathVariable("token") String token){
        String str = userService.checkVerifyUser(userId, token);
        return str;
    }
    //Role admin
    @RequestMapping(value = "/user/{userID}/role", method = RequestMethod.PUT)
    public String updateRole(@PathVariable("userID") String userId, HttpServletRequest request){
        String pageAccessToken = request.getHeader(Constant.PAGE_TOKEN_HEADER);
        return userService.editRoleUser(userId, pageAccessToken);
    }

    //Role admin
    @RequestMapping(value = "/user/{userID}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("userID") String userId, HttpServletRequest request){
        String tokenPage = request.getHeader(Constant.PAGE_TOKEN_HEADER);
        return userService.deleteUser(userId, tokenPage);
    }
}
