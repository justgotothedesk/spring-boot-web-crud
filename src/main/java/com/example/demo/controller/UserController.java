package com.example.demo.controller;
import com.example.demo.domain.Article;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Optional;

@Controller
@SessionAttributes("user")
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
    public String logout(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
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

    @GetMapping("/IDCheck")
    public @ResponseBody ResponseDto<?> check(String id){

        if(id == null || id.isEmpty()){
            return new ResponseDto<>(-1,"아이디를 입력해주세요",null);
        }
        if (!userService.IDcheck(id)){
            return new ResponseDto<>(1,"동일한 아이디가 존재합니다.", false);
        }else{
            return new ResponseDto<>(1,"사용가능한 아이디입니다.", true);
        }
    }

    @GetMapping("/NicknameCheck")
    public @ResponseBody ResponseDto<?> nickcheck(String nickname){

        if(nickname == null || nickname.isEmpty()){
            return new ResponseDto<>(-1,"닉네임을 입력해주세요",null);
        }
        if (!userService.Nickcheck(nickname)){
            return new ResponseDto<>(1,"동일한 닉네임이 존재합니다.", false);
        }else{
            return new ResponseDto<>(1,"사용가능한 닉네임입니다.", true);
        }
    }

    @GetMapping("users/update/{id}")
    public String updateForm(@PathVariable String id, Model model) {
        Optional<User> user = userService.findOne(id);
        if (user.isEmpty()) {
            return  "redirect:/info";
        }
        model.addAttribute("user", user.orElse(null));

        return "users/updateUser";
    }

    @PostMapping("users/update/{id}")
    public String update(@PathVariable String id, User newUser) {
        userService.update(id, newUser);

        return "redirect:/info";
    }
}
