package com.higgsup.smm.dto;

/**
 * Created by StormSpirit on 1/11/2017.
 */
public class InviteUserDTO {
    private String invitedByName;
    private String emailDes;
    private String role;
    private String pageId;

    public String getEmailDes() {
        return emailDes;
    }

    public void setEmailDes(String emailDes) {
        this.emailDes = emailDes;
    }

    public String getInvitedByName() {
        return invitedByName;
    }

    public void setInvitedByName(String invitedByName) {
        this.invitedByName = invitedByName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }
}
