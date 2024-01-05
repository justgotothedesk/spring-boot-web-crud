package com.example.demo.service;

import com.example.demo.domain.Article;
import com.example.demo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
//@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Long create(Article article) {
        articleRepository.save(article);
        return article.getId();
    }

    public List<Article> findArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> findOne(Long id) {
        return articleRepository.findById(id);
    }

    public Long update(Long id, Article article) {
        Article originarticle = articleRepository.findById(id).get();
        originarticle.setTitle(article.getTitle());
        originarticle.setContent(article.getContent());
        originarticle.setWriter(article.getWriter());

        return article.getId();
    }

    public void deleteOne(Long id) {
        articleRepository.delete(id);
    }
}
