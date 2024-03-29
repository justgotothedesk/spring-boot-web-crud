package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String create(User user) {
        userRepository.save(user);
        return user.getId();
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findOne(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> findOneN(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    public Optional<User> findByIDAndPassword(String id, String password) {
        return userRepository.findByIDAndPassword(id, password);
    }

    public boolean IDcheck(String id) {
        return userRepository.IDcheck(id);
    }

    public boolean Nickcheck(String nickname) {
        return userRepository.Nickcheck(nickname);
    }

    public String update(String id, User newUser) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User originUser = optionalUser.get();
            originUser.setNickname(newUser.getNickname());
            originUser.setPassword(newUser.getPassword());

            return originUser.getId();
        } else {
            return null;
        }

    }
}
