package com.epita.tinyx.gateway.controller.dto;

import com.epita.tinyx.gateway.entity.User;
import lombok.Getter;

@Getter
public class UserDTO {
    private String username;

    private String role;

    private String id;

    private String imageUri;

    private String bannerUri;

    public UserDTO() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setBannerUri(String bannerUri) {
        this.bannerUri = bannerUri;
    }

    public UserDTO(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public UserDTO(User user) {
        this.username = user.username;
        this.role = user.role;
        this.id = user.id.toString();
        this.imageUri = user.imageUri;
        this.bannerUri = user.bannerUri;
    }

    public User toEntity() {
        return new User(username, role, imageUri, bannerUri);
    }
}
