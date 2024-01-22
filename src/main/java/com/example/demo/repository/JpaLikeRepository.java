package com.example.demo.repository;

import com.example.demo.domain.Recog;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaLikeRepository implements LikeRepository {

    private final EntityManager em;
    @Getter
    public enum LikeType {
        LIKE(1),
        DISLIKE(-1);

        private final int value;

        LikeType(int value) {
            this.value = value;
        }

    }

    public JpaLikeRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Recog> findById(Long articleId, String userId) {
        String jpql = "SELECT l FROM Recog l WHERE l.article.id = :articleId AND l.user.id = :userId";
        try {
            Recog recog = em.createQuery(jpql, Recog.class)
                    .setParameter("articleId", articleId)
                    .setParameter("userId", userId)
                    .getSingleResult();
            return Optional.ofNullable(recog);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long countLikes(Long articleId) {
        String jpql = "SELECT COUNT(l) FROM Recog l WHERE l.article.id = :articleId AND l.type = :likeType";
        try {
            return em.createQuery(jpql, Long.class)
                    .setParameter("articleId", articleId)
                    .setParameter("likeType", LikeType.LIKE.getValue())
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    @Override
    public Long countDislikes(Long articleId) {
        String jpql = "SELECT COUNT(l) FROM Recog l WHERE l.article.id = :articleId AND l.type = :likeType";
        try {
            return em.createQuery(jpql, Long.class)
                    .setParameter("articleId", articleId)
                    .setParameter("likeType", LikeType.DISLIKE.getValue())
                    .getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    @Override
    public void save(Recog recog) {
        em.persist(recog);
    }

    @Override
    public void delete(Recog recog) {
        em.remove(recog);
    }
}

