package com.higgsup.smm.service;


import com.higgsup.smm.constant.FacebookAPIConstant;
import com.higgsup.smm.constant.FacebookCommentData;
import com.higgsup.smm.constant.FacebookListComment;
import com.higgsup.smm.dto.CommentDTO;
import com.higgsup.smm.model.entity.*;
import com.higgsup.smm.model.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.higgsup.smm.service.ConvertString.splitCommaRuleWord;
import static com.higgsup.smm.service.ConvertString.unAccent;

/**
 * Created by DangThanhLinh on 11/01/2017.
 */
@Service
public class CommentService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuthUserRepository oAuthUserRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PageAcessTokenRepository pageAcessTokenRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RuleRepository ruleRepository;

    public CommentDTO addComment(CommentDTO commentDTO) {
        OAuthUser oAuthUser = oAuthUserRepository.findByProviderUserId(commentDTO.getProviderUserId());

        Comment comment = new Comment();
        comment.setAssignUser(oAuthUser);
        comment.setHide(commentDTO.isHide());
        comment.setPostId(commentDTO.getPostId());
        comment.setReviewed(commentDTO.isReviewed());
        comment.setCommentId(commentDTO.getCommentId());

        commentRepository.save(comment);

        return commentDTO;
    }

    public List<CommentDTO> getListComment(String postId) {

        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setProviderUserId(comment.getAssignUser().getProviderUserId());
            commentDTO.setPostId(comment.getPostId());
            commentDTO.setReviewed(comment.isReviewed());
            commentDTO.setHide(comment.isHide());
            commentDTOs.add(commentDTO);
        }
        return commentDTOs;
    }

    public CommentDTO editComment(String commentID, CommentDTO commentDTO) {
        Comment comment = commentRepository.findByCommentId(commentID);
        OAuthUser oAuthUser = oAuthUserRepository.findByProviderUserId(commentDTO.getProviderUserId());

        comment.setHide(commentDTO.isHide());
        comment.setReviewed(commentDTO.isReviewed());
        comment.setAssignUser(oAuthUser);
        comment.setPostId(commentDTO.getPostId());
        commentRepository.save(comment);
        return commentDTO;
    }

    public PageAccessToken saveAccessToken(PageAccessToken pageAccessToken, String pageId) {
        Page page = pageRepository.findByPageId(pageId);

        PageAccessToken tokenPage = new PageAccessToken();
        tokenPage.setAccessToken(pageAccessToken.getAccessToken());
        tokenPage.setPage(page);
        pageAcessTokenRepository.save(tokenPage);
        return tokenPage;
    }


    public String hideComment(String commentId, String token) {
        String hidden = "is_hidden=true";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("comment_id", commentId);
        parameters.put("access_token", token);

        String response = restTemplate.postForObject(
                FacebookAPIConstant.FB_COMMENTS,
                hidden,
                String.class,
                parameters);
        return response;
    }

    public String unHideComment(String commentId, String token) {
        String hidden = "is_hidden=false";
        Map<String, String> parameters = new HashMap<>();
        parameters.put("comment_id", commentId);
        parameters.put("access_token", token);

        String response = restTemplate.postForObject(
                FacebookAPIConstant.FB_COMMENTS,
                hidden,
                String.class,
                parameters);
        return response;
    }

    public String getComment(String commentId, String token) {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("comment_id", commentId);
        parameters.put("access_token", token);

        return restTemplate.getForObject(
                FacebookAPIConstant.FB_COMMENTS,
                String.class,
                parameters);
    }

    public void deleteComment(String commentId, String token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("comment_id", commentId);
        parameters.put("access_token", token);
        restTemplate.delete(FacebookAPIConstant.FB_COMMENTS, parameters);
    }


    public FacebookListComment getCommentOfPost(String postId, String token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("post_id", postId);
        parameters.put("fields", "comments{message,is_hidden,from,comments{message,is_hidden,from}}");
        parameters.put("access_token", token);

        return restTemplate.getForObject(
                FacebookAPIConstant.FB_COMMENTS_POST,
                FacebookListComment.class,
                parameters);
    }

    public List<FacebookCommentData> checkRuleForComment(String postId, String token) {
        PageAccessToken pageAccessToken = pageAcessTokenRepository.findByAccessToken(token);
        List<Rule> rules = ruleRepository.findByPage(pageAccessToken.getPage());

        FacebookListComment listComment = getCommentOfPost(postId, token);
        List<FacebookCommentData> comments = new ArrayList<>();
        comments.addAll(listComment.getComments().getData());

        // load all comment
        for (FacebookCommentData comment : comments) {
            //load all rule approve
            for (Rule rule : rules) {
                List<String> ruleWord = splitCommaRuleWord(unAccent(rule.getRuleWords()));
                //check contain ruleWord in Comment
                if (stringContainsItemFromList(comment.getMessage(), ruleWord)) {
                    System.out.println(comment.getMessage());
                    doRule(comment, rule, token, comments);
                }
            }
        }
        //merge 2 list
        List<Comment> commentList = commentRepository.findByPostId(postId);
        for (FacebookCommentData commentData: comments){
            for (Comment comment: commentList){
                if (comment.getCommentId().equals(commentData.getId()) ){
                    commentData.setReviewed(comment.isReviewed());

                }
            }
        }
        //save to db
        for (FacebookCommentData comment : comments) {
            addCommentToDB(comment, postId);
        }


        return comments;
    }

    //check contain ruleWord in Comment
    private void doRule(FacebookCommentData comment, Rule rule, String token, List<FacebookCommentData> comments) {
        if (rule.isRemove()) {
            deleteComment(comment.getId(), token);
            removeCommentInDB(comment.getId());
            comments.remove(comment);
        } else if (rule.isHide()) {
            hideComment(comment.getId(), token);
            comment.setReviewed(false);
            comment.setHidden(true);
        } else if (rule.isApprove()) {
            comment.setReviewed(true);
        }
        if (rule.getAssignUser() != null) {
            comment.setProviderUserId(rule.getAssignUser().getProviderUserId());
        }
    }

    private boolean stringContainsItemFromList(String comment, List<String> ruleWord) {
        for (String rule : ruleWord) {
            if (unAccent(comment).contains(rule)) {
                return true;
            }
        }
        return false;
    }

    //add Comment to DataBase
    private void addCommentToDB(FacebookCommentData commentData, String postId) {
        OAuthUser oAuthUser = oAuthUserRepository.findByProviderUserId(commentData.getProviderUserId());

        if (commentRepository.findByCommentId(commentData.getId()) == null) {
            Comment comment = new Comment();
            comment.setCommentId(commentData.getId());
            comment.setPostId(postId);
            comment.setHide(commentData.isHidden());
            comment.setAssignUser(oAuthUser);
            comment.setReviewed(commentData.isReviewed());
            commentRepository.save(comment);
        } else {
            Comment comment = commentRepository.findByCommentId(commentData.getId());
            comment.setReviewed(commentData.isReviewed());
            comment.setHide(commentData.isHidden());

            commentRepository.save(comment);
        }
    }

    //remove Comment In DB
    private void removeCommentInDB(String commentId) {
        Comment comment = commentRepository.findByCommentId(commentId);
        commentRepository.delete(comment);
    }

    //hide Comment In DB
    private void hideCommentInDB(String commentId, boolean isHidden) {
        Comment comment = commentRepository.findByCommentId(commentId);
        comment.setHide(isHidden);
        comment = commentRepository.save(comment);
    }


}
