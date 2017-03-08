package com.higgsup.smm.controller;

import com.higgsup.smm.constant.FacebookCommentData;
import com.higgsup.smm.constant.FacebookListComment;
import com.higgsup.smm.dto.CommentDTO;
import com.higgsup.smm.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by DangThanhLinh on 11/01/2017.
 */
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/addComment")
    public CommentDTO addComment(@RequestBody CommentDTO commentDTO) {
        return commentService.addComment(commentDTO);
    }

    //get Lisst Comment.
    @GetMapping("/{post_id}/getListComment")
    public List<CommentDTO> getListComment(@PathVariable("post_id") String postId) {
        return commentService.getListComment(postId);
    }

    //edit Comment (if user has this comment)
    @PutMapping("comment/{comment_id}")
    public CommentDTO editComment(@PathVariable("comment_id") String commentID, @RequestBody CommentDTO commentDTO) {
        return commentService.editComment(commentID, commentDTO);
    }

    //hide Comment
    @PostMapping("comment/hide/{comment_id}")
    public String hideCommment(@PathVariable("comment_id") String commentId, HttpServletRequest request) {
        String token = request.getHeader("access_token");
        return commentService.hideComment(commentId, token);
    }

    //unhide Comment
    @PostMapping("comment/unhide/{comment_id}")
    public String unHideCommment(@PathVariable("comment_id") String commentId, HttpServletRequest request) {
        String token = request.getHeader("access_token");
        return commentService.unHideComment(commentId, token);
    }

    //get Comment
    @GetMapping("comment/{comment_id}")
    public String getCommment(@PathVariable("comment_id") String commentId, HttpServletRequest request) {
        String token = request.getHeader("access_token");
        return commentService.getComment(commentId, token);
    }

    //delete Comment
    @DeleteMapping("comment/{comment_id}")
    public void deleteComment(@PathVariable("comment_id") String commentId, HttpServletRequest request) {
        String token = request.getHeader("access_token");
        commentService.deleteComment(commentId, token);
    }


    //get cmt theo postId
    @GetMapping("comment/api/{post_id}")
    public FacebookListComment listComment(@PathVariable("post_id") String postId, HttpServletRequest request) {
        String token = request.getHeader("access_token");
        return commentService.getCommentOfPost(postId, token);
    }

    @GetMapping("do-in-post/{post_id}")
    public List<FacebookCommentData> doInPost(@PathVariable("post_id") String postId, HttpServletRequest request) {
        String token = request.getHeader("access_token");
        return commentService.checkRuleForComment(postId, token);
    }


}
