package com.example.demo.repository;

import com.example.demo.domain.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    void save(Comment comment);
    Optional<Comment> findByArticleId(Long articleId);
}

