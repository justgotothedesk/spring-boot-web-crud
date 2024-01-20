package com.example.demo.controller;

import com.example.demo.domain.Article;
import com.example.demo.domain.Comment;
import com.example.demo.domain.User;
import com.example.demo.service.ArticleService;
import com.example.demo.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final ArticleService articleService;
    private final HttpSession session;

    @Autowired
    public CommentController(CommentService commentService, ArticleService articleService, HttpSession session) {
        this.commentService = commentService;
        this.articleService = articleService;
        this.session = session;
    }

    @PostMapping("/comments/create")
    public String create(@RequestParam Long articleId, @RequestParam String content, HttpSession session) {
        Comment comment = new Comment();
        Optional<Article> articleOptional = articleService.findOne(articleId);

        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            comment.setArticle(article);
            User temp = (User) session.getAttribute("user");
            comment.setUser(temp);
            comment.setContent(content);
            commentService.create(comment);
        } else {
            throw new RuntimeException("Article not found with ID: " + articleId);
        }

        return "redirect:/articles/" + articleId;
    }



    @GetMapping("/comments/getByArticleId/{articleId}")
    public Optional<Comment> getCommentsByArticleId(@PathVariable Long articleId) {
        return commentService.getCommentsByArticleId(articleId);
    }
}
