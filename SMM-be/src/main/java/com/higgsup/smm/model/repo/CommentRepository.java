package com.higgsup.smm.model.repo;

import com.higgsup.smm.model.entity.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by DangThanhLinh on 11/01/2017.
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, String> {
    Comment findByCommentId(String commentId);

    List<Comment> findByPostId(String postId);
}
