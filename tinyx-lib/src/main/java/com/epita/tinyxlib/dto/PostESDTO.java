package com.epita.tinyxlib.dto;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@ApplicationScoped
public class PostESDTO {

    public String id;
    public String text;
    public List<String> hashtags;
    public String author;


    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public PostESDTO() {
    }

    public PostESDTO(PostDTO post) {
        this.hashtags = Arrays.stream(post.text.split("\\s+")).filter(s -> s.startsWith("#")).map(s -> s.substring(1)).toList();
        String textWithoutHashtags = Arrays.stream(post.text.split("\\s+")).filter(s -> !s.startsWith("#")).toList().stream().reduce("", (a, b) -> a + " " + b);
        this.id = post.id;
        this.text = textWithoutHashtags;
        this.author = post.author;
    }
}
