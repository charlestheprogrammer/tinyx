package com.epita.post.controller;

import com.epita.tinyxlib.dto.CreatePostDTO;
import com.epita.tinyxlib.dto.PostDTO;
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
    public void createPost(@HeaderParam("X-user-id") String userId, CreatePostDTO postDTO) {
        if (userId == null || userId.isEmpty())
            throw new BadRequestException("X-user-id header is missing");
        if (postDTO.getText().length() > 160)
            throw new BadRequestException("Text is too long");
        PostDTO post = postDTO.toPostDTO();
        post.setAuthor(userId);
        postService.createPost(post.toEntity());
    }

    @POST
    @Path("/reply/{postId}")
    public void createReply(CreatePostDTO postDTO, @PathParam("postId") String postId, @HeaderParam("X-user-id") String userId) {
        if (userId == null || userId.isEmpty())
            throw new BadRequestException("X-user-id header is missing");
        if (postDTO.getText().length() > 160)
            throw new BadRequestException("Text is too long");
        PostDTO post = postDTO.toPostDTO();
        post.setAuthor(userId);
        post.replyTo = postId;
        postService.createReply(post.toEntity());
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

    @GET
    @Path("/post/by_id_get_author/{id}")
    public String getPostAuthorById(String id) {
        return postService.getPostById(id).author.toString();
    }

    @GET
    @Path("/replies/{postId}")
    public List<PostDTO> getReplies(@PathParam("postId") String postId, @QueryParam("limit") int limit, @QueryParam("offset") int offset) {
        if (limit == 0)
            limit = 50;
        return postService.getReplies(postId, limit, offset).stream().map(PostDTO::new).toList();
    }

    @DELETE
    @Path("/post/{postId}")
    public void deletePost(@PathParam("postId") String postId, @HeaderParam("X-user-id") String userId) {
        if (userId == null || userId.isEmpty())
            throw new BadRequestException("X-user-id header is missing");
        postService.deletePost(postId, userId);
    }
}
