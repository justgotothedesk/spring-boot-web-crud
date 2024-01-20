package com.example.demo.repository;

import com.example.demo.domain.Article;
import com.example.demo.domain.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    void save(Comment comment);
    Optional<List<Comment>> findByArticleId(Long articleId);
    Optional<Comment> findById(Long id);
    void delete(Long id);
}

