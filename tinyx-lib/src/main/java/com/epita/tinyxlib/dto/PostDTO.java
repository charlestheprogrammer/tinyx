package com.epita.tinyxlib.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PostDTO {
    private UUID _id;
    private UserDTO author;
    private LocalDateTime postedAt;
    private Integer likesCount;  // TODO: do we want to have a list of users who liked instead ?
    private Integer commentsCount;  // TODO: do we want to have a list of comments (posts) instead ?
    private PostContentDTO content;
    private PostDTO parentPost;
}
