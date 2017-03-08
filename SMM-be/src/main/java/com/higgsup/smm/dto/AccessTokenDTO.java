package com.higgsup.smm.dto;

public class AccessTokenDTO {
    private String token;
    private OAuthUserDTO user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OAuthUserDTO getUser() {
        return user;
    }

    public void setUser(OAuthUserDTO user) {
        this.user = user;
    }
}
