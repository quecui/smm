package com.higgsup.smm.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PageAccessToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long pageAccessTokenId;

    @Column(nullable = false)
    private String accessToken;

    private String refreshToken;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "page_id")
    private Page page;

    @ManyToMany
    @JoinTable(
            name = "user_has_pagetoken",
            joinColumns = @JoinColumn(name = "page_access_token_id ", referencedColumnName = "pageAccessTokenId "),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "providerUserId "))
    private List<OAuthUser> users = new ArrayList<OAuthUser>();

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getPageAccessTokenId() {
        return pageAccessTokenId;
    }

    public void setPageAccessTokenId(Long pageAccessTokenId) {
        this.pageAccessTokenId = pageAccessTokenId;
    }

    public List<OAuthUser> getUsers() {
        return users;
    }

    public void setUsers(List<OAuthUser> users) {
        this.users = users;
    }
}