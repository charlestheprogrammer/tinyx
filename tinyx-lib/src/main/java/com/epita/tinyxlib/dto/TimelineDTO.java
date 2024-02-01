package com.epita.tinyxlib.dto;


import com.epita.tinyxlib.entities.Timeline;

import java.util.List;

public class TimelineDTO {
    private List<TimelineItemDTO> posts;

    private String userId;

    public TimelineDTO() {
    }

    public TimelineDTO(List<TimelineItemDTO> posts, String userId) {
        this.posts = posts;
        this.userId = userId;
    }

    public TimelineDTO(Timeline timeline) {
        this.posts = timeline.posts;
        this.userId = timeline.userId.toString();
    }

    public List<TimelineItemDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<TimelineItemDTO> posts) {
        this.posts = posts;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
