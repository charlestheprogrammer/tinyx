package com.epita.post.controller;

import com.epita.post.controller.dto.PostDTO;
import com.epita.post.service.PostService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;

import java.util.List;

@RequestScoped
@Path("/api/")
@Produces("application/json")
@Consumes("application/json")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GET
    @Path("/posts")
    public List<PostDTO> getPosts() {
        return postService.getPosts().stream().map(PostDTO::new).toList();
    }

    @POST
    @Path("/post")
    public void createPost(PostDTO postDTO) {
        postService.createPost(postDTO.toEntity());
    }

    @GET
    @Path("/posts/by_author/{author}")
    public List<PostDTO> getPostsByAuthor(String author) {
        return postService.getPostsByAuthor(author).stream().map(PostDTO::new).toList();
    }

    @GET
    @Path("/post/by_id/{id}")
    public PostDTO getPostById(String id) {
        return new PostDTO(postService.getPostById(id));
    }
}
