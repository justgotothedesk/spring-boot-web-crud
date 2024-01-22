package com.example.demo.controller;

import com.example.demo.service.LikeService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like/{articleId}/{type}")
    public String likeDislike(@PathVariable Long articleId, @PathVariable int type) {
        likeService.likeDislike(articleId, type);
        return "redirect:/articles/"+articleId;
    }

    @Getter
    @Setter
    public static class LikeRequest {
        private Long articleId;
        private int type;
    }
}
