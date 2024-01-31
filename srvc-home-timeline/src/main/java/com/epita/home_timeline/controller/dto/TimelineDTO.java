package com.epita.home_timeline.controller.dto;


import com.epita.home_timeline.entity.Timeline;

import java.util.List;

public class TimelineDTO {
    private List<String> posts;

    private String userId;

    public TimelineDTO() {
    }

    public TimelineDTO(List<String> posts, String userId) {
        this.posts = posts;
        this.userId = userId;
    }

    public TimelineDTO(Timeline timeline) {
        this.posts = timeline.posts;
        this.userId = timeline.userId.toString();
    }

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
