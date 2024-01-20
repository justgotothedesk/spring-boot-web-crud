package com.example.demo.repository;

import com.example.demo.domain.Article;
import com.example.demo.domain.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
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
    public Optional<List<Comment>> findByArticleId(Long articleId) {
        List<Comment> result = em.createQuery("select c from Comment c where c.article.id=:articleId", Comment.class)
                .setParameter("articleId", articleId)
                .getResultList();

        return Optional.of(result);
    }
    @Override
    public Optional<Comment> findById(Long id) {
        Comment comment = em.find(Comment.class, id);
        return Optional.ofNullable(comment);
    }
    @Override
    public void delete(Long id) {
        Comment comment = findById(id).orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        em.remove(comment);
    }
}
