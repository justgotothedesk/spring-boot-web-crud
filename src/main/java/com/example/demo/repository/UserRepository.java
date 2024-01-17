package com.example.demo.repository;
import com.example.demo.domain.Article;
import com.example.demo.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByName(String name);
    Optional<User> findByNickname(String nickname);
    List<User> findAll();
    void delete(String id);
}
