package com.epita.social.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class Block extends PanacheMongoEntity {
    public ObjectId blocker;

    public ObjectId blocked;

    public LocalDateTime date;

    public boolean isActive;

    public Block() {
    }

    public Block(ObjectId blocker, ObjectId blocked) {
        this.blocker = blocker;
        this.blocked = blocked;
        this.date = LocalDateTime.now();
        this.isActive = true;
    }

    public static Block findBlock(ObjectId blocker, ObjectId blocked) {
        return find("blocker = ?1 and blocked = ?2", blocker, blocked).firstResult();
    }

    public static Block findActiveBlock(ObjectId blocker, ObjectId blocked) {
        return find("blocker = ?1 and blocked = ?2 and isActive = ?3", blocker, blocked, true).firstResult();
    }

    public static Block findBlock(ObjectId id) {
        return find("_id", id).firstResult();
    }

    public static List<Block> findBlockedBy(ObjectId blocker) {
        return find("blocker = ?1 and isActive = ?2", blocker, true).list();
    }

    public static List<Block> findBlockedBy(ObjectId blocker, int limit, int offset) {
        return find("blocker = ?1 and isActive = ?2", blocker, true).page(offset, limit).list();
    }

    public static List<Block> findBlocked(ObjectId blocked) {
        return find("blocked = ?1 and isActive = ?2", blocked, true).list();
    }
}
