package com.example.demo.controller;

import com.example.demo.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleForm {
    private String title;
    private String content;
    private User user;
}
