package com.epita.post.controller.dto;

import lombok.Getter;

@Getter
public class CreatePostDTO {
    private String text;

    private String media;

    private String repost;

    public void setText(String text) {
        this.text = text;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public void setRepost(String repost) {
        this.repost = repost;
    }

    public CreatePostDTO() {
    }

    public CreatePostDTO(String text, String media, String repost) {
        this.text = text;
        this.media = media;
        this.repost = repost;
    }

    public PostDTO toPostDTO() {
        PostDTO postDTO = new PostDTO();
        postDTO.setText(text);
        postDTO.setMedia(media);
        postDTO.setRepost(repost);
        return postDTO;
    }
}
