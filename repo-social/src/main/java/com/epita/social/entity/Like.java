package com.epita.social.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Getter
public class Like extends PanacheMongoEntity {
    public ObjectId postId;

    public ObjectId userId;

    public LocalDateTime date;

    public Like() {
    }

    public Like(ObjectId postId, ObjectId userId) {
        this.postId = postId;
        this.userId = userId;
        this.date = LocalDateTime.now();
    }

    public static Like findLike(ObjectId postId, ObjectId userId) {
        return find("postId = ?1 and userId = ?2", postId, userId).firstResult();
    }

    public static Like findLike(ObjectId id) {
        return find("_id", id).firstResult();
    }

    public static List<Like> findLikes(ObjectId postId) {
        return find("postId", postId).list();
    }

    public static List<Like> findLikesFromUser(ObjectId userId) {
        return find("userId", userId).list();
    }

    public static long countLikes(ObjectId postId) {
        return count("postId", postId);
    }

    public static long countLikes(ObjectId postId, ObjectId userId) {
        return count("postId = ?1 and userId = ?2", postId, userId);
    }

    public static long countLikesBy(ObjectId userId) {
        return count("userId", userId);
    }

    public static long countLikesBy(ObjectId postId, ObjectId userId) {
        return count("postId = ?1 and userId = ?2", postId, userId);
    }
}
