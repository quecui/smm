package com.higgsup.smm.model.entity;

import javax.persistence.*;

/**
 * Created by StormSpirit on 1/11/2017.
 */
@Entity
public class VerifyUser {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    int id;
    private String role;
    private String userId;
    private boolean status;
    private String tokenVerify;
    private String pageId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTokenVerify() {
        return tokenVerify;
    }

    public void setTokenVerify(String tokenVerify) {
        this.tokenVerify = tokenVerify;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }
}
