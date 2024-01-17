package com.example.demo.controller;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

        return "home";
    }
}
