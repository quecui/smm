package com.higgsup.smm.dto;

/**
 * Created by nguye on 09/01/2017.
 */
public class PageDTO {
    private String pageId;
    private String avatarPageUrl;
    private String pageName;
    private String accessToken;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getAvatarPageUrl() {
        return avatarPageUrl;
    }

    public void setAvatarPageUrl(String avatarPageUrl) {
        this.avatarPageUrl = avatarPageUrl;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }


    public String getAccessToken() {
        return accessToken;
    }
}
