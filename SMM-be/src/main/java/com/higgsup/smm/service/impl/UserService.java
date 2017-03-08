package com.higgsup.smm.service.impl;

import com.higgsup.smm.dto.InviteUserDTO;
import com.higgsup.smm.dto.UserDTO;
import com.higgsup.smm.model.entity.*;
import com.higgsup.smm.model.enums.OAuthProvider;
import com.higgsup.smm.model.repo.*;
import com.higgsup.smm.service.impl.verify.BuildTemplate;
import com.higgsup.smm.service.impl.verify.VerifyService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by StormSpirit on 1/10/2017.
 */
@Service
public class UserService {
    @Autowired
    private OAuthUserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private VerifyUserRepository verifyUserRepository;
    @Autowired
    private PageAcessTokenRepository pageAcessTokenRepository;
    @Autowired
    private OAuthAccessTokenRepository oAuthAccessTokenRepository;

    public List<UserDTO> getAllUsers(String tokenUser, String pageAccessToken) {
//        OAuthAccessToken oAuthAccessToken = oAuthAccessTokenRepository.findByAccessToken(tokenUser);
//        String userId1 = oAuthAccessToken.getUser().getProviderUserId();
//
//        OAuthUser oAuthUser1 = userRepository.findByProviderUserId(userId1);
//        List<PageAccessToken> pageAccessTokens = oAuthUser1.getPageAccessTokens();
//        PageAccessToken pageToken1 = new PageAccessToken();
//        for(int j = 0; j < pageAccessTokens.size(); j++){
//            if(pageAccessTokens.get(j).getAccessToken().equals(pageAccessToken)){
//                pageToken1 = pageAccessTokens.get(j);
//            }
//        }

       // PageAccessToken page = pageAcessTokenRepository.findByAccessToken(pageAccessToken); // Error
        String pageId = new String();
        List<PageAccessToken> pageAccessTokens = (List<PageAccessToken>) pageAcessTokenRepository.findAll();
        for(int i = 0; i < pageAccessTokens.size(); i++){
            if(pageAccessTokens.get(i).getAccessToken().equals(pageAccessToken)){
                pageId = pageAccessTokens.get(i).getPage().getPageId();
                break;
            }
        }
        List<Role> roles = roleRepository.findByPagePageId(pageId);
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();

        for(int i = 0; i < roles.size(); i++){
            String userId = roles.get(i).getUser().getProviderUserId();
            OAuthUser oAuthUser = userRepository.findByProviderUserId(userId);

            if(oAuthUser == null)
                continue;

            UserDTO userDTO = new UserDTO();
            userDTO.setPageId(roles.get(i).getPage().getPageId());
            userDTO.setAvatarUrl(oAuthUser.getAvatarUrl());
            userDTO.setProviderUserId(userId);
            userDTO.setProvider(OAuthProvider.FACEBOOK);
            userDTO.setEmail(oAuthUser.getEmail());
            userDTO.setProfileUrl(oAuthUser.getProfileUrl());
            userDTO.setRole(roles.get(i).getRole());
            userDTO.setName(oAuthUser.getName());

            userDTOs.add(userDTO);

        }
        return userDTOs;
    }


    public String inviteUser(InviteUserDTO inviteUserDTO) throws TemplateException, IOException, MessagingException {
        List<Role> roles = roleRepository.findByPagePageId(inviteUserDTO.getPageId());

//        for(int i = 0; i < roles.size(); i++){
//            String userID = roles.get(i).getUser().getProviderUserId();
//            OAuthUser user = userRepository.findByProviderUserId(userID);
//            String email = user.getEmail();
//            if(email.equals(inviteUserDTO.getEmailDes()))
//                return "fail";
//        }

//        OAuthUser user = userRepository.findByEmail(inviteUserDTO.getEmailDes());
//        if(user != null)
//            return "fail";

        String verifyToken = UUID.randomUUID().toString();
        String url = "http://localhost:9000/#/app/verify/" + verifyToken;
        String pageName = getNamePageByPageID(inviteUserDTO.getPageId());
        String nameUser = inviteUserDTO.getInvitedByName();
        String role = inviteUserDTO.getRole();

        VerifyService verify = new VerifyService();
        BuildTemplate template = new BuildTemplate();
        verify.sendVerifiedEmail(inviteUserDTO.getEmailDes(), template.buildTemplateEmail(nameUser, role, pageName, url));

        //Save User infomation to DB
        VerifyUser verifyUser = new VerifyUser();
        verifyUser.setPageId(inviteUserDTO.getPageId());
        verifyUser.setStatus(false);
        verifyUser.setTokenVerify(verifyToken);
        verifyUser.setRole(inviteUserDTO.getRole());
        verifyUserRepository.save(verifyUser);

        return "success";
    }

    public String checkVerifyUser(String userId, String token) {

        VerifyUser user = verifyUserRepository.findByTokenVerify(token);


        if (user == null)
            return "fail";

        Role roleCheck = roleRepository.findByUserProviderUserIdAndPagePageId(userId, user.getPageId());
        OAuthUser oAuthUser = userRepository.findByProviderUserId(userId);
        Page page = pageRepository.findByPageId(user.getPageId());
        if (roleCheck != null || oAuthUser == null)
            return "fail";

        user.setStatus(true);
        Role role = new Role();
        role.setRole(user.getRole());
        role.setUser(oAuthUser);
        role.setPage(page);
        roleRepository.save(role);

        PageAccessToken pageAccessToken = pageAcessTokenRepository.findByPagePageId(user.getPageId());

        PageAccessToken newPageAccessToken = new PageAccessToken();
        newPageAccessToken.setPage(page);
        newPageAccessToken.setAccessToken(pageAccessToken.getAccessToken());
        newPageAccessToken.getUsers().add(oAuthUser);
        pageAcessTokenRepository.save(newPageAccessToken);
        verifyUserRepository.delete(user);
        return "success";
    }

    public String getNamePageByPageID(String pageId) {
        Page page = pageRepository.findOne(pageId);
        return page.getPageName();
    }

    public String deleteUser(String userId, String tokenPage) {
        OAuthUser user = userRepository.findOne(userId);
        List<PageAccessToken> pageAccessTokenList = (List<PageAccessToken>) pageAcessTokenRepository.findAll();

        String pageId = "";
        for(int i = 0; i < pageAccessTokenList.size(); i++){
            if(pageAccessTokenList.get(i).getAccessToken().equals(tokenPage)){ //&& pageAccessTokenList.get(i).getUsers().get(i).getProviderUserId().equals(userId)
                pageId = pageAccessTokenList.get(i).getPage().getPageId();
                break;
            }
        }
        //PageAccessToken pageAccessToken = pageAcessTokenRepository.findByAccessToken(tokenPage);
        Role role = roleRepository.findByUserProviderUserIdAndPagePageId(userId, pageId);

        if (user == null || role == null)
            return "fail";

        Page page = pageRepository.findByPageId(pageId);
        String adminRootId = page.getAdminRootId();

        if(adminRootId.equals(userId))
            return "fail";

        List<PageAccessToken> pageAccessTokens = userRepository.findByProviderUserId(userId).getPageAccessTokens();
        for(int i = 0; i < pageAccessTokens.size(); i++){
            if(pageAccessTokens.get(i).getPage().getPageId().equals(pageId)){
                pageAcessTokenRepository.delete(pageAccessTokens.get(i));
            }
        }

        roleRepository.delete(role);

        return "success";
    }


    public String editRoleUser(String userId, String tokenPage) {//UserDTO userDTO
        OAuthUser oAuthUser = userRepository.findOne(userId); // user id
        List<PageAccessToken> pageAccessToken1s = (List<PageAccessToken>) pageAcessTokenRepository.findAll();

        String pageId = new String();
        for(int i = 0; i < pageAccessToken1s.size(); i++){
            if(pageAccessToken1s.get(i).getAccessToken().equals(tokenPage)){
                pageId = pageAccessToken1s.get(i).getPage().getPageId();
                break;
            }
        }
        //PageAccessToken pageAccessToken = pageAcessTokenRepository.findByAccessToken(tokenPage);//?
        if (oAuthUser == null)
            return "fail";

        Role role = roleRepository.findByUserProviderUserIdAndPagePageId(userId, pageId); // page id
        Page page = pageRepository.findByPageId(pageId);
        String adminRootId = page.getAdminRootId();

        if (role == null || role.getUser().getProviderUserId().equals(adminRootId))
            return "fail";

        if(role.getRole().equals("ADMIN"))
            role.setRole("MODERATOR");
        else
            role.setRole("ADMIN");

        roleRepository.save(role);

        return "success";
    }
}
