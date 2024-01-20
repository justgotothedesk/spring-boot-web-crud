package com.example.demo.repository;

import com.example.demo.domain.Article;
import com.example.demo.domain.Comment;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCommentRepository implements CommentRepository{
    private final EntityManager em;

    public JpaCommentRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Comment comment) {
        em.persist(comment);
    }

    @Override
    public Optional<Comment> findByArticleId(Long articleId) {
        List<Comment> result = em.createQuery("select c from Comment c where c.article.id=:articleId", Comment.class)
                .setParameter("articleId", articleId)
                .getResultList();

        return result.stream().findAny();
    }
}
