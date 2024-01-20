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

    public Long update(Long id, Article newArticle) {
        Optional<Article> optionalArticle = articleRepository.findById(id);

        if (optionalArticle.isPresent()) {
            Article originArticle = optionalArticle.get();
            originArticle.setTitle(newArticle.getTitle());
            originArticle.setContent(newArticle.getContent());

            return originArticle.getId();
        } else {
            return null;
        }
    }

    public void deleteOne(Long id, Article article) {
        Optional<Article> optionalArticle = articleRepository.findById(id);

        if (optionalArticle.isPresent()) {
            if (article.getContent().equals("삭제한다")) {
                articleRepository.delete(id);
            }
        }
    }
}
