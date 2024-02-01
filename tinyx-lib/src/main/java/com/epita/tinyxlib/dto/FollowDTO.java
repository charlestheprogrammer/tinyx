package com.epita.tinyxlib.dto;

import com.epita.tinyxlib.entities.Follow;
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

