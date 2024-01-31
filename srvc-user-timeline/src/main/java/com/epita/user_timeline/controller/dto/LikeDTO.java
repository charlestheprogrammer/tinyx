package com.epita.user_timeline.controller.dto;

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
}
