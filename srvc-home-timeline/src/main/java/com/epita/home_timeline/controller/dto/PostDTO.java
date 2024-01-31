package com.epita.home_timeline.controller.dto;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@ApplicationScoped
public class PostDTO {
    public String id;
    public String text;

    public String media;

    public String repost;

    public String author;

    public LocalDateTime created_date;

    public String replyTo;

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public void setRepost(String repost) {
        this.repost = repost;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public PostDTO() {
    }
}
