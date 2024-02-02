package com.example.demo.repository;

import com.example.demo.domain.Article;
import com.example.demo.domain.Comment;
import com.example.demo.domain.File;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaFileRepository implements FileRepository{

    private final EntityManager em;

    public JpaFileRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public File save(File file) {
        em.persist(file);
        return file;
    }

    @Override
    public void delete(Long id) {
        File file = findById(id).orElseThrow(() -> new EntityNotFoundException("File not found"));
        em.remove(file);
    }

    @Override
    public List<File> findAll() {
        return em.createQuery("select f from File f", File.class)
                .getResultList();
    }

    @Override
    public Optional<File> findById(Long id) {
        File file = em.find(File.class, id);
        return Optional.ofNullable(file);
    }

    @Override
    public Optional<List<File>> findByArticleId(Long articleId) {
        List<File> result = em.createQuery("select f from File f where f.article.id=:articleId", File.class)
                .setParameter("articleId", articleId)
                .getResultList();

        return Optional.of(result);
    }
}
