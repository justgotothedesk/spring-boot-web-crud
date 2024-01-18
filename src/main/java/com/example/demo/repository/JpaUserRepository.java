package com.example.demo.repository;

import com.example.demo.domain.Article;
import com.example.demo.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository{
    private final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }
    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }
    @Override
    public Optional<User> findByName(String name) {
        List<User> result = em.createQuery("select u from User u where u.name=:name", User.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }
    @Override
    public Optional<User> findByNickname(String nickname) {
        List<User> result = em.createQuery("select u from User u where u.nickname=:nickname", User.class)
                .setParameter("nickname", nickname)
                .getResultList();

        return result.stream().findAny();
    }
    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
    @Override
    public void delete(String id) {
        User user = findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        em.remove(user);
    }

    @Override
    public Optional<User> findByIDAndPassword(String id, String password) {
        List<User> result = em.createQuery("select u from User u where u.id=:id and u.password=:password", User.class)
                .setParameter("id", id)
                .setParameter("password", password)
                .getResultList();

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}
