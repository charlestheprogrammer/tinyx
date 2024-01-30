package com.epita.post.controller.dto;

import com.epita.post.entity.Post;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import lombok.Getter;
import org.bson.types.ObjectId;

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

    public PostDTO(Post post) {
        this.id = post.id.toString();
        this.text = post.text;
        this.author = post.author.toString();
        this.media = post.media;
        this.created_date = post.created_date;
        if (post.repost != null)
            this.repost = post.repost.toString();
        if (post.replyTo != null)
            this.replyTo = post.replyTo.toString();
    }

    public Post toEntity() {
        try {
            ObjectId postAuthor = author != null ? new ObjectId(this.author) : null;
            ObjectId postRepost = repost != null ? new ObjectId(this.repost) : null;
            ObjectId postReplyTo = this.replyTo != null ? new ObjectId(this.replyTo) : null;
            return new Post(text, postAuthor, postRepost, media, postReplyTo);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid id");
        }
    }
}
