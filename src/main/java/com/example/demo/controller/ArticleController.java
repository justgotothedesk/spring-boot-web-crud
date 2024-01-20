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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final CommentService commentService;
    private final HttpSession session;

    @Autowired
    public ArticleController(ArticleService articleService, CommentService commentService, HttpSession session) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.session = session;
    }

    @GetMapping("/articles/new")
    public String createForm() {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "users/loginForm";
        }

        return "articles/createArticleForm";
    }

    @PostMapping("/articles/new")
    public String create(ArticleForm form) {
        Article article = new Article();
        article.setTitle(form.getTitle());
        User temp = (User) session.getAttribute("user");
        article.setUser(temp);
        article.setContent(form.getContent());
        articleService.create(article);
        return "articles/articleList";
    }

    @GetMapping("articles/")
    public String list(Model model) {
        List<Article> articles = articleService.findArticles();
        model.addAttribute("articles", articles);

        return "articles/articleList";
    }

    @GetMapping("articles/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Optional<Article> article = articleService.findOne(id);
        Optional<List<Comment>> comments = commentService.getCommentsByArticleId(id);
        User user = (User) session.getAttribute("user");
        if (article.isEmpty()) {
            return "redirect:/articles/";
        }
        model.addAttribute("article", article.orElse(null));
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);

        return "articles/articleDetail";
    }

    @GetMapping("articles/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        Optional<Article> article = articleService.findOne(id);
        if (article.isEmpty()) {
            return  "redirect:/articles/";
        }
        model.addAttribute("article", article.orElse(null));

        return "articles/updateArticle";
    }

    @PostMapping("articles/update/{id}")
    public String update(@PathVariable Long id, Article newArticle) {
        articleService.update(id, newArticle);

        return "redirect:/articles/";
    }

    @GetMapping("articles/delete/{id}")
    public String deleteForm(@PathVariable Long id, Model model) {
        Optional<Article> article = articleService.findOne(id);
        if (article.isEmpty()) {
            return "redirect:/articles/";
        }
        model.addAttribute("article", article.orElse(null));

        return "articles/deleteArticle";
    }

    @PostMapping("articles/delete/{id}")
    public String delete(@PathVariable Long id, Article article) {
        articleService.deleteOne(id, article);

        return "redirect:/articles/";
    }
}
