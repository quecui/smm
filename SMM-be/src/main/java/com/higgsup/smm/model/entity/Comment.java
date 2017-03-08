package com.higgsup.smm.model.entity;


import javax.persistence.*;
import java.io.Serializable;


/**
 * Created by DangThanhLinh on 30/12/2016.
 */
@Entity
public class Comment implements Serializable {

    @Id
    private String commentId;

    @Column(nullable = false)
    private String postId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provider_user_id")
    private OAuthUser assignUser;

    private boolean isHide;
    private boolean isReviewed;


    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    public String getCommentId() {
        return commentId;
    }


    public OAuthUser getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(OAuthUser assignUser) {
        this.assignUser = assignUser;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

}
