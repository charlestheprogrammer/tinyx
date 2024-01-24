package com.epita.tinyxlib.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
    private UUID _id;
    private String username;
    private String displayName;
    private Integer followersCount;
    private Integer followingCount;
}
