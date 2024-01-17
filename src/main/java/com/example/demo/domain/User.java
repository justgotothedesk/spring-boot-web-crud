package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
    @Id
    private String id;
    private String name;
    private String password;
    private String nickname;

    public User() {
        this.id = id;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "User{"+
                "id="+id+
                ", name="+name+
                ", password="+password+
                ", nickname="+nickname+"}";
    }
}
