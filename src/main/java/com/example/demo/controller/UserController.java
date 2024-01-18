package com.example.demo.controller;
import com.example.demo.domain.Article;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    @Autowired
    public UserController(UserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    @GetMapping("/register")
    public String createForm() {
        return "users/registerForm";
    }

    @PostMapping("/register")
    public String create(UserForm form) {
        User user = new User();
        user.setId(form.getId());
        user.setName(form.getName());
        user.setNickname(form.getNickname());
        user.setPassword(form.getPassword());
        userService.create(user);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String createLoginForm() {
        return "users/loginForm";
    }

    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password) {
        Optional<User> userOptional = userService.findByIDAndPassword(id, password);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/info")
    public String detail(Model model) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            model.addAttribute("user", user);
            return "users/userInfo";
        } else {
            return "redirect:/login";
        }
    }
}
