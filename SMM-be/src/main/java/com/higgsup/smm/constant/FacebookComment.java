package com.higgsup.smm.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DangThanhLinh on 11/01/2017.
 */
public class FacebookComment {
    private List<FacebookCommentData> data = new ArrayList<>();

    public List<FacebookCommentData> getData() {
        return data;
    }

    public void setData(List<FacebookCommentData> data) {
        this.data = data;
    }
}
