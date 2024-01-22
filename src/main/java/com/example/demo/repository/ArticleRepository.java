package com.example.demo.repository;

import com.example.demo.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);
    Optional<Article> findById(Long id);
    Optional<Article> findByTitle(String title);
    Optional<Article> findByContent(String content);
    Optional<Article> findByUserID(String id);
    List<Article> findAll();
    void delete(Long id);

    void like(Long id);
    void dislike(Long id);
    void mlike(Long id);
    void mdislike(Long id);
}
