package com.higgsup.smm.dto;

/**
 * Created by DangThanhLinh on 09/01/2017.
 */
public class RuleDTO {
    private Long ruleId;

    private String ruleName;
    private String ruleWords;
    private boolean approve;
    private boolean remove;
    private boolean hide;
    private String assignUserID;
    private String pageId;

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleWords() {
        return ruleWords;
    }

    public void setRuleWords(String ruleWords) {
        this.ruleWords = ruleWords;
    }

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getAssignUserID() {
        return assignUserID;
    }

    public void setAssignUserID(String assignUserID) {
        this.assignUserID = assignUserID;
    }
}

