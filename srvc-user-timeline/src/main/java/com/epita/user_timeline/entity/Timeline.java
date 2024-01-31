package com.epita.user_timeline.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.types.ObjectId;

import java.util.List;

public class Timeline extends PanacheMongoEntity {
    public List<String> posts;

    public ObjectId userId;

    public Timeline() {
    }

    public Timeline(List<String> posts, ObjectId userId) {
        this.posts = posts;
        this.userId = userId;
    }

    public static Timeline findByUserId(ObjectId userId) {
        System.out.println("userId: " + userId);
        return find("userId", userId).firstResult();
    }
}
