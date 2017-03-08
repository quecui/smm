package com.higgsup.smm.model.entity;

import javax.persistence.*;

/**
 * Created by DangThanhLinh on 30/12/2016.
 */
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provider_user_id")
    private OAuthUser user; //providerUserId

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "page_id")
    private Page page;
    private String role;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }


    public OAuthUser getUser() {
        return user;
    }

    public void setUser(OAuthUser user) {
        this.user = user;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}

