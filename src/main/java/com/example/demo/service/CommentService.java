package com.example.demo.service;

import com.example.demo.domain.Article;
import com.example.demo.domain.Comment;
import com.example.demo.domain.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CommentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void create(Comment comment) {
        commentRepository.save(comment);
    }

    public Optional<Comment> getCommentsByArticleId(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }
}

