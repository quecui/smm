package com.higgsup.smm.service;

import com.higgsup.smm.dto.PageDTO;
import com.higgsup.smm.model.entity.*;
import com.higgsup.smm.model.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguye on 09/01/2017.
 */
@Service
public class PageService {
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private PageAcessTokenRepository pageAcessTokenRepository;
    @Autowired
    private OAuthAccessTokenRepository oAuthAccessTokenRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    OAuthUserRepository oAuthUserRepository;

    public PageDTO savePage(PageDTO pageDTO, String token) {

        OAuthAccessToken oAuthAccessToken = oAuthAccessTokenRepository.findByAccessToken(token);

        if (pageRepository.findByPageId(pageDTO.getPageId()) == null) {


            Page newPage = new Page();
            newPage.setPageId(pageDTO.getPageId());
            newPage.setAvatarPageUrl(pageDTO.getAvatarPageUrl());
            newPage.setPageName(pageDTO.getPageName());
            newPage.setAdminRootId(oAuthAccessToken.getUser().getProviderUserId());

            pageRepository.save(newPage);

            PageAccessToken pageAccessToken = new PageAccessToken();
            pageAccessToken.setAccessToken(pageDTO.getAccessToken());
            pageAccessToken.setPage(newPage);

            pageAccessToken.getUsers().add(oAuthAccessToken.getUser());
            pageAcessTokenRepository.save(pageAccessToken);

            Role role = new Role();
            role.setRole("ADMIN");
            role.setPage(newPage);
            role.setUser(oAuthAccessToken.getUser());
            roleRepository.save(role);

        } else {
            /*
            Nếu đã tồn tại page rồi, thì cập nhật page access token cho page đó
            Điều này khác với old code: vì old code tạo thêm 1 record mới, thậm chí duplicate
             */
            Page page = pageRepository.findByPageId(pageDTO.getPageId());
            //old code: PageAccessToken pageAccessToken = new PageAccessToken();
            /*new code*/PageAccessToken pageAccessToken = pageAcessTokenRepository.findByPage(page);
            pageAccessToken.setAccessToken(pageDTO.getAccessToken());
            pageAccessToken.setPage(page);
            pageAccessToken.getUsers().add(oAuthAccessToken.getUser());
            //old code không có 5 dòng dưới
            List<PageAccessToken> existedPageAccessTokens = (List<PageAccessToken>) pageAcessTokenRepository.findAll();
            for (PageAccessToken accessToken: existedPageAccessTokens) {
                if(accessToken.getAccessToken().equals(pageAccessToken.getAccessToken()))
                     throw new NullPointerException("Page existed");
            }
            pageAcessTokenRepository.save(pageAccessToken);

            Role role = new Role();
            role.setRole("ADMIN");
            role.setPage(page);
            role.setUser(oAuthAccessToken.getUser());
            roleRepository.save(role);
        }

        return pageDTO;
    }

    public List<PageDTO> pageList(String token) {
        List<PageDTO> pages = new ArrayList<PageDTO>();
        OAuthAccessToken oAuthAccessToken = oAuthAccessTokenRepository.findByAccessToken(token);
        OAuthUser oAuthUser = oAuthAccessToken.getUser();
        List<Role> roles = roleRepository.findByUser(oAuthUser);
        for (Role role : roles) {
            Page page = role.getPage();
            PageDTO pageDTO = new PageDTO();
            pageDTO.setPageId(page.getPageId());
            pageDTO.setPageName(page.getPageName());
            pageDTO.setAvatarPageUrl(page.getAvatarPageUrl());
            pages.add(pageDTO);

        }
        return pages;
    }

    public PageDTO getPageDetail(String pageId, String token) {
        PageDTO pageDetail = new PageDTO();
        Page page = pageRepository.findByPageId(pageId);
        OAuthAccessToken oAuthAccessToken = oAuthAccessTokenRepository.findByAccessToken(token);
        OAuthUser oAuthUser = oAuthAccessToken.getUser();
        PageAccessToken pageAccessToken = pageAcessTokenRepository.findByPageAndUsers(page, oAuthUser);


        pageDetail.setPageId(page.getPageId());
        pageDetail.setAvatarPageUrl(page.getAvatarPageUrl());
        pageDetail.setPageName(page.getPageName());
        pageDetail.setAccessToken(pageAccessToken.getAccessToken());
        return pageDetail;
    }
}
