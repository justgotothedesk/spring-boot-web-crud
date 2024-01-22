package com.example.demo.controller;

import com.example.demo.domain.Article;
import com.example.demo.domain.Comment;
import com.example.demo.domain.User;
import com.example.demo.service.ArticleService;
import com.example.demo.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public Optional<List<Comment>> getCommentsByArticleId(@PathVariable Long articleId) {
        return commentService.getCommentsByArticleId(articleId);
    }

    @GetMapping("/comments/delete/{id}-{commentId}")
    public String deleteForm(@PathVariable Long id, @PathVariable Long commentId) {
        commentService.delete(commentId);
        return "redirect:/articles/"+id;
    }

    @GetMapping("/comments/update/{id}-{commentId}")
    public String updateForm(@PathVariable Long id, @PathVariable Long commentId, Model model) {
        Optional<Article> article = articleService.findOne(id);
        Optional<Comment> comment = commentService.findOne(commentId);
        model.addAttribute("article", article.orElse(null));
        model.addAttribute("comment", comment.orElse(null));

        return "comments/updateComment";
    }

    @PostMapping("/comments/update/{id}-{commentId}")
    public String update(@PathVariable Long id, @PathVariable Long commentId, String content) {
        Comment temp = new Comment();
        temp.setContent(content);
        commentService.update(commentId, temp);

        return "redirect:/articles/"+id;
    }
}
