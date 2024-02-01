package com.epita.dto;

import lombok.Data;

@Data
public class PostContentDTO {
    private String text;
    private String media;  // TODO: change to Media type
    private PostDTO quotedPostDTO;
}
