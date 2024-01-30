package com.epita.social.controller;

import com.epita.social.service.LikeService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import org.bson.types.ObjectId;

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
}
