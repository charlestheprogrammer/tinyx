package com.epita.social.controller;

import com.epita.social.service.FollowService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import org.bson.types.ObjectId;

import java.util.List;

@RequestScoped
@Consumes("application/json")
@Produces("application/json")
@Path("/api")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }
    @POST
    @Path("/follow/{followed}")
    public void follow(@HeaderParam("X-user-id") String follower, @PathParam("followed") String followed) {
        if (follower == null || follower.isEmpty()) {
            throw new BadRequestException("X-user-id header is missing");
        }
        ObjectId followerObject;
        ObjectId followedObject;
        try {
            followerObject = new ObjectId(follower);
            followedObject = new ObjectId(followed);
        } catch (Exception e) {
            throw new BadRequestException("Invalid user id");
        }
        followService.follow(followerObject, followedObject);
    }

    @POST
    @Path("/unfollow/{followed}")
    public void unfollow(@HeaderParam("X-user-id") String follower, @PathParam("followed") String followed) {
        if (follower == null || follower.isEmpty()) {
            throw new BadRequestException("X-user-id header is missing");
        }
        ObjectId followerObject;
        ObjectId followedObject;
        try {
            followerObject = new ObjectId(follower);
            followedObject = new ObjectId(followed);
        } catch (Exception e) {
            throw new BadRequestException("Invalid user id");
        }
        followService.unfollow(followerObject, followedObject);
    }

    @GET
    @Path("/followed/{followed}")
    public boolean isFollowed(@HeaderParam("X-user-id") String follower, @PathParam("followed") String followed) {
        if (follower == null || follower.isEmpty()) {
            throw new BadRequestException("X-user-id header is missing");
        }
        ObjectId followerObject;
        ObjectId followedObject;
        try {
            followerObject = new ObjectId(follower);
            followedObject = new ObjectId(followed);
        } catch (Exception e) {
            throw new BadRequestException("Invalid user id");
        }
        return followService.isFollowed(followerObject, followedObject);
    }

    @GET
    @Path("/followers")
    public List<String> getFollowers(@HeaderParam("X-user-id") String followed) {
        if (followed == null || followed.isEmpty()) {
            throw new BadRequestException("X-user-id header is missing");
        }
        ObjectId followedObject;
        try {
            followedObject = new ObjectId(followed);
        } catch (Exception e) {
            throw new BadRequestException("Invalid user id");
        }
        return followService.getFollowers(followedObject).stream().map(follow -> follow.follower.toString()).toList();
    }

    @GET
    @Path("/follows")
    public List<String> getFollows(@HeaderParam("X-user-id") String follower) {
        if (follower == null || follower.isEmpty()) {
            throw new BadRequestException("X-user-id header is missing");
        }
        ObjectId followerObject;
        try {
            followerObject = new ObjectId(follower);
        } catch (Exception e) {
            throw new BadRequestException("Invalid user id");
        }
        return followService.getFollows(followerObject).stream().map(follow -> follow.followed.toString()).toList();
    }
}
