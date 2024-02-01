package com.epita.tinyxlib.entities;

import com.epita.tinyxlib.dto.TimelineItemDTO;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import org.bson.types.ObjectId;

import java.util.List;

public class Timeline extends PanacheMongoEntity {
    public List<TimelineItemDTO> posts;

    public ObjectId userId;

    public Timeline() {
    }

    public Timeline(List<TimelineItemDTO> posts, ObjectId userId) {
        this.posts = posts;
        this.userId = userId;
    }

    public static Timeline findByUserId(ObjectId userId) {
        return find("userId", userId).firstResult();
    }
}
