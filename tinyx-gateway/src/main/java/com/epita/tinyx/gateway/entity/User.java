package com.epita.tinyx.gateway.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.UUID;

@Getter
public class User extends PanacheMongoEntity {

    public String username;

    public String role;

    public String imageUri;

    public String bannerUri;

    public User() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setBannerUri(String bannerUri) {
        this.bannerUri = bannerUri;
    }

    public User(String username, String role, String imageUri, String bannerUri) {
        this.username = username;
        this.role = role;
        this.imageUri = imageUri;
        this.bannerUri = bannerUri;
    }

    public static User findUserByUsername(String username) {
        return find("username", username).firstResult();
    }

    public static User findUserByUsernameAndRole(String username, String role) {
        return find("username = ?1 and role = ?2", username, role).firstResult();
    }

    public static User findUserById(String id) {
        return find("_id", new ObjectId(id)).firstResult();
    }
}
