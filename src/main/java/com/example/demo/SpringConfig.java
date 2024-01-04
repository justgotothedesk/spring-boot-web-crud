package com.example.demo;

import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.JpaArticleRepository;
import com.example.demo.service.ArticleService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public ArticleRepository articleRepository() {
        return new JpaArticleRepository(em);
    }

    @Bean
    public ArticleService articleService() {
        return new ArticleService(articleRepository());
    }
}
