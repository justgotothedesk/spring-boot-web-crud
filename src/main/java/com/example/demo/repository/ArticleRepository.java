package com.example.demo.repository;

import com.example.demo.domain.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article save(Article article);
    Optional<Article> findById(Long id);
    Optional<Article> findByTitle(String title);
    Optional<Article> findByContent(String content);
    Optional<Article> findByWriter(String writer);
    List<Article> findAll();
    void delete(Long id);
}
