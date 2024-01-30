package com.epita.social.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class Follow extends PanacheMongoEntity {
    public ObjectId follower;

    public ObjectId followed;

    public Follow() {
    }

    public Follow(ObjectId follower, ObjectId followed) {
        this.follower = follower;
        this.followed = followed;
    }

    public static Follow findFollow(ObjectId follower, ObjectId followed) {
        return find("follower = ?1 and followed = ?2", follower, followed).firstResult();
    }

    public static Follow findFollow(ObjectId id) {
        return find("_id", id).firstResult();
    }

    public static List<Follow> findFollowedBy(ObjectId follower) {
        return find("follower = ?1", follower).list();
    }

    public static List<Follow> findFollowedBy(ObjectId follower, int limit, int offset) {
        return find("follower = ?1", follower).page(offset, limit).list();
    }

    public static List<Follow> findFollows(ObjectId followed) {
        return find("followed = ?1", followed).list();
    }

    public static List<Follow> findFollows(ObjectId followed, int limit, int offset) {
        return find("followed = ?1", followed).page(offset, limit).list();
    }
}
