package com.epita.tinyxlib.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection="Users")
public class User extends PanacheMongoEntity {
    // getters and setters
    private String username;
    private String displayName;
    private Integer followersCount;
    private Integer followingCount;
    private LocalDateTime createdAt;

    public static User findByUsername(String username) {
        return find("username", username).firstResult();
    }

    public static User findByID(String id) {
        return find("id", id).firstResult();
    }

    public static List<User> findAllUsers() {
        return findAll().list();
    }
}
