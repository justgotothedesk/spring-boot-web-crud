package com.example.demo.service;

import com.example.demo.domain.Article;
import com.example.demo.domain.Recog;
import com.example.demo.domain.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.LikeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final HttpSession session;
    private final ArticleRepository articleRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, HttpSession session, ArticleRepository articleRepository) {
        this.likeRepository = likeRepository;
        this.session = session;
        this.articleRepository = articleRepository;
    }

    public void likeDislike(Long articleId, int type) {
        User user = (User) session.getAttribute("user");
        Optional<Recog> likeOptional = likeRepository.findById(articleId, user.getId());
        Optional<Article> articleOptional = articleRepository.findById(articleId);

        if (likeOptional.isEmpty() && articleOptional.isPresent()) {
            Recog temp = new Recog();
            temp.setType(type);
            temp.setUser(user);
            temp.setArticle(articleOptional.get());

            likeRepository.save(temp);
            if (type == 1) {
                articleRepository.like(articleId);
            } else {
                articleRepository.dislike(articleId);
            }
        } else if (likeOptional.isPresent() && articleOptional.isPresent()) {
            Recog temp = likeOptional.get();
            if (temp.getType() != type) {
                temp.setType(type);
                likeRepository.save(temp);

                if (type == 1) {
                    articleRepository.like(articleId);
                    articleRepository.mdislike(articleId);
                } else {
                    articleRepository.dislike(articleId);
                    articleRepository.mlike(articleId);
                }

            } else {
                likeRepository.delete(temp);
                if (type == 1) {
                    articleRepository.mlike(articleId);
                } else {
                    articleRepository.mdislike(articleId);
                }
            }
        }
    }
    public Long countLikes(Long articleId) {
        return likeRepository.countLikes(articleId);
    }

    public Long countDislikes(Long articleId) {
        return likeRepository.countDislikes(articleId);
    }
}
