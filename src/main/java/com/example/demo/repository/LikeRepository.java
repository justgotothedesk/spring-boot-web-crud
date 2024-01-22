package com.example.demo.repository;

import com.example.demo.domain.Recog;

import java.util.Optional;

public interface LikeRepository {

    Optional<Recog> findById(Long articleId, String userId);

    Long countLikes(Long articleId);

    Long countDislikes(Long articleId);

    void save(Recog recog);
    void delete(Recog recog);
}
