package com.higgsup.smm.constant;

/**
 * Created by DangThanhLinh on 17/01/2017.
 */
public class FacebookCommentData {
    private String id;
    private String message;
    private FacebookComment comments;
    private boolean isHidden;

    private boolean isReviewed;
    private String providerUserId;

    public FaceBookCommentUser getFrom() {
        return from;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    private FaceBookCommentUser from;

    public void setFrom(FaceBookCommentUser from) {
        this.from = from;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FacebookComment getComments() {
        return comments;
    }

    public void setComments(FacebookComment comments) {
        this.comments = comments;
    }
}
