package com.epita.social.controller.dto;

import com.epita.social.entity.Follow;
import org.bson.types.ObjectId;

public class FollowDTO {
    public ObjectId followerId;

    public ObjectId followingId;

    public FollowDTO() {
    }

    public FollowDTO(Follow follow) {
        this.followerId = follow.getFollower();
        this.followingId = follow.getFollowed();
    }
}
