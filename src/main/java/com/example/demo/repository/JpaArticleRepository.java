package com.example.demo.repository;

import com.example.demo.domain.Article;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public class JpaArticleRepository implements ArticleRepository{
    private final EntityManager em;

    public JpaArticleRepository(EntityManager em) {
        this.em = em;
    }
    @Override
    public Article save(Article article) {
        em.persist(article);
        return article;
    }

    @Override
    public Optional<Article> findById(Long id) {
        Article article = em.find(Article.class, id);
        return Optional.ofNullable(article);
    }

    @Override
    public Optional<Article> findByTitle(String title) {
        List<Article> result = em.createQuery("select a from Article a where a.title=:title", Article.class)
                .setParameter("title", title)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public Optional<Article> findByContent(String content) {
        List<Article> result = em.createQuery("select a from Article a where a.content=:content", Article.class)
                .setParameter("content", content)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public Optional<Article> findByWriter(String writer) {
        List<Article> result = em.createQuery("select a from Article a where a.writer=:writer", Article.class)
                .setParameter("writer", writer)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Article> findAll() {
        return em.createQuery("select a from Article a", Article.class)
                .getResultList();
    }
}
