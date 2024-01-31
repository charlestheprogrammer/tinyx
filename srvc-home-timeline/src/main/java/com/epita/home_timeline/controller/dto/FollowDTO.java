package com.epita.home_timeline.controller.dto;

import org.bson.types.ObjectId;

public class FollowDTO {
    public ObjectId followerId;

    public ObjectId followingId;

    public FollowDTO() {
    }
}
