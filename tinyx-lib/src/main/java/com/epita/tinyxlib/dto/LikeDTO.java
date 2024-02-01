package com.epita.tinyxlib.dto;

import com.epita.tinyxlib.entities.Like;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Getter
public class LikeDTO {
    public ObjectId postId;

    public ObjectId userId;

    public LocalDateTime date;

    public LikeDTO() {
    }

    public LikeDTO(Like like) {
        this.postId = like.getPostId();
        this.userId = like.getUserId();
        this.date = like.getDate();
    }
}