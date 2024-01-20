package com.example.demo.repository;

import com.example.demo.domain.Article;
import com.example.demo.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public class JpaArticleRepository implements ArticleRepository{
    private final EntityManager em;
    private final HttpSession session;

    public JpaArticleRepository(EntityManager em, HttpSession session) {
        this.em = em;
        this.session = session;
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
    public Optional<Article> findByUserID(String id) {
        List<Article> result = em.createQuery("SELECT a FROM Article a WHERE a.user.id = :id", Article.class)
                .setParameter("id", id)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Article> findAll() {
        return em.createQuery("select a from Article a", Article.class)
                .getResultList();
    }

    @Override
    public void delete(Long id) {
        Article article = findById(id).orElseThrow(() -> new EntityNotFoundException("Article not found"));
        em.remove(article);
    }
}
