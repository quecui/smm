package com.higgsup.smm.model.entity;

import javax.persistence.*;

/**
 * Created by DangThanhLinh on 30/12/2016.
 */
@Entity
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ruleId;

    private String ruleName;
    private String ruleWords;
    private boolean approve;
    private boolean remove;
    private boolean hide;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provider_user_id")
    private OAuthUser assignUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "page_id")
    private Page page;


    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
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

    public OAuthUser getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(OAuthUser assignUser) {
        this.assignUser = assignUser;
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
}
