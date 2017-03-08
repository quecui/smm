package com.higgsup.smm.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by nguye on 09/01/2017.
 */
@Entity
public class Page implements Serializable {
    @Id
    @Column(nullable = false)
    private String pageId;

    @Column(nullable = false)
    private String pageName;


    @Column(nullable = false)
    private String adminRootId;

    @Column(nullable = false)
    private String avatarPageUrl;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getAdminRootId() {
        return adminRootId;
    }

    public void setAdminRootId(String adminRootId) {
        this.adminRootId = adminRootId;
    }

    public String getAvatarPageUrl() {
        return avatarPageUrl;
    }

    public void setAvatarPageUrl(String avatarPageUrl) {
        this.avatarPageUrl = avatarPageUrl;
    }
}