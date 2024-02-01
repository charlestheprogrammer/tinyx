package com.epita.tinyxlib.dto;

public class TimelineItemDTO {
    public String postId;

    public String userWhoLiked;

    public TimelineItemDTO(String postId, String userWhoLiked) {
        this.postId = postId;
        this.userWhoLiked = userWhoLiked;
    }

    public TimelineItemDTO() {
    }
}
