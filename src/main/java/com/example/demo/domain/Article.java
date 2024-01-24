package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Getter
@Setter
@Entity
@ToString
@Document(indexName = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recog> recommendations;

    private int likeCount;
    private int dislikeCount;

    @Column(name = "search_text", columnDefinition = "TEXT")
    private String searchText;

    public Article() {}

    public Article(Long id, String title, String content, User user, List<Recog> recommendations, int likeCount, int dislikeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.recommendations = recommendations;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.searchText = title + " " + content + " " + user.getNickname();
    }
}
