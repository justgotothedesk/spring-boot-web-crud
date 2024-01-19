package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String writer;

    public Article() {}
    public Article(Long id, String title, String content, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "Article{"+
                "id="+id+
                ", title="+title+
                ", content="+content+
                ", writer="+writer+"}";
    }
}
