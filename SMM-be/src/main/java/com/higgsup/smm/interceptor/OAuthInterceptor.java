package com.higgsup.smm.interceptor;

import com.higgsup.smm.constant.Constant;
import com.higgsup.smm.exception.ExceptionMessage;
import com.higgsup.smm.exception.ServiceException;
import com.higgsup.smm.model.entity.*;
import com.higgsup.smm.model.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by DangThanhLinh on 30/12/2016.
 */
@Component
public class OAuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private OAuthAccessTokenRepository oAuthAccessTokenRepository;

    @Autowired
    private PageAcessTokenRepository pageAccessTokenRepository;

    @Autowired
    private OAuthUserRepository oAuthUserRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true; // Don't process non-handler method
        }

        HandlerMethod method = (HandlerMethod) handler;
        if (method.getMethodAnnotation(NoAuthenticationCheck.class) != null) {
            return true; // Bypass all services that is annotated with @NoAuthenticationCheck
        }


        String tokenOAuthInHeader = request.getHeader(Constant.AUTH_TOKEN_HEADER);
        String tokenPageInHeader = request.getHeader(Constant.PAGE_TOKEN_HEADER);
        if (tokenOAuthInHeader == null) {
            throw new ServiceException(ExceptionMessage.INVALID_TOKEN);
        }


        if (tokenPageInHeader != null) {

            OAuthAccessToken oAuthAccessToken = oAuthAccessTokenRepository.findByAccessToken(tokenOAuthInHeader);

            PageAccessToken pageAccessToken = pageAccessTokenRepository.findByAccessToken(tokenPageInHeader);

            OAuthUser oAuthUser = oAuthUserRepository.findByProviderUserId(oAuthAccessToken.getUser().getProviderUserId());

            Page page = pageRepository.findByPageId(pageAccessToken.getPage().getPageId());

            Role role = roleRepository.findByUserAndPage(oAuthUser, page);

            if (method.getMethodAnnotation(RoleAdmin.class) != null) {
                if (role.getRole().equals(Constant.ADMIN)) ;
                return true;
            } else if (method.getMethodAnnotation(RoleModerator.class) != null) {
                if (role.getRole().equals(Constant.MODERATOR)) ;
                return true;
            }
        }
        throw new ServiceException(ExceptionMessage.CONTACT_NOT_FOUND_EXCEPTION);
    }
}

