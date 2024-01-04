package com.example.demo.controller;

import com.example.demo.domain.Article;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles/new")
    public String createForm() {
        return "articles/createArticleForm";
    }

    @PostMapping("/articles/new")
    public String create(ArticleForm form) {
        Article article = new Article();
        article.setTitle(form.getTitle());
        article.setContent(form.getContent());
        article.setWriter(form.getWriter());

        articleService.create(article);

        return "redirect:/";
    }
}
