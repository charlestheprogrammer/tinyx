package com.epita.social.controller;

import com.epita.social.service.LikeService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import org.bson.types.ObjectId;

import java.util.List;

@RequestScoped
@Consumes("application/json")
@Produces("application/json")
@Path("/api")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @POST
    @Path("/like/{postId}")
    public void createLike(@HeaderParam("X-user-id") String userId, @PathParam("postId") String postId) {
        ObjectId postObjectId;
        ObjectId userObjectId;
        try {
            postObjectId = new ObjectId(postId);
            userObjectId = new ObjectId(userId);
        } catch (Exception e) {
            throw new BadRequestException("Invalid id");
        }
        likeService.createLike(postObjectId, userObjectId);
    }

    @GET
    @Path("/likes/post/{postId}")
    public List<String> getLikes(@PathParam("postId") String postId) {
        ObjectId postObjectId;
        try {
            postObjectId = new ObjectId(postId);
        } catch (Exception e) {
            throw new BadRequestException("Invalid id");
        }
        return likeService.getLikes(postObjectId);
    }

    @GET
    @Path("/likes/user/{userId}")
    public List<String> postsLikedBy(@PathParam("userId") String userId) {
        ObjectId userObjectId;
        try {
            userObjectId = new ObjectId(userId);
        } catch (Exception e) {
            throw new BadRequestException("Invalid id");
        }
        return likeService.postsLikedBy(userObjectId);
    }
}
