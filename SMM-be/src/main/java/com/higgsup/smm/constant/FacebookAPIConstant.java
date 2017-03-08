package com.higgsup.smm.constant;

public class FacebookAPIConstant {
    private static final String FB_GRAPH_API_ENDPOINT = "https://graph.facebook.com/v2.7/";
    public static final String FB_EXCHANGE_TOKEN_URL_TEMPLATE = FB_GRAPH_API_ENDPOINT + "/oauth/access_token?grant_type=fb_exchange_token&client_id={client_id}&client_secret={client_secret}&fb_exchange_token={fb_exchange_token}";
    public static final String FB_USER_PROFILE_URL_TEMPLATE = FB_GRAPH_API_ENDPOINT + "/me?fields={fields}&access_token={access_token}";
    public static final String FB_COMMENTS = FB_GRAPH_API_ENDPOINT + "/{comment_id}?access_token={access_token}";
    public static final String FB_COMMENTS_POST = FB_GRAPH_API_ENDPOINT + "/{post_id}/?fields={fields}&access_token={access_token}";


}
