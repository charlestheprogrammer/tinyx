package com.epita.social.controller;

import com.epita.social.service.BlockService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import org.bson.types.ObjectId;

import java.util.List;

@RequestScoped
@Consumes("application/json")
@Produces("application/json")
@Path("/api")
public class BlockController {
    private final BlockService blockService;
    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @POST
    @Path("/block/{blocked}")
    public void block(@HeaderParam("X-user-id") String blocker, @PathParam("blocked") String blocked) {
        if (blocker == null || blocker.isEmpty()) {
            throw new BadRequestException("X-user-id header is missing");
        }
        ObjectId blockerObject;
        ObjectId blockedObject;
        try {
            blockerObject = new ObjectId(blocker);
            blockedObject = new ObjectId(blocked);
        } catch (Exception e) {
            throw new BadRequestException("Invalid user id");
        }
        blockService.block(blockerObject, blockedObject);
    }

    @POST
    @Path("/unblock/{blocked}")
    public void unblock(@HeaderParam("X-user-id") String blocker, @PathParam("blocked") String blocked) {
        if (blocker == null || blocker.isEmpty()) {
            throw new BadRequestException("X-user-id header is missing");
        }
        ObjectId blockerObject;
        ObjectId blockedObject;
        try {
            blockerObject = new ObjectId(blocker);
            blockedObject = new ObjectId(blocked);
        } catch (Exception e) {
            throw new BadRequestException("Invalid user id");
        }
        blockService.unblock(blockerObject, blockedObject);
    }

    @GET
    @Path("/blocked/{blocked}")
    public boolean isBlocked(@HeaderParam("X-user-id") String blocker, @PathParam("blocked") String blocked) {
        if (blocker == null || blocker.isEmpty()) {
            throw new BadRequestException("X-user-id header is missing");
        }
        ObjectId blockerObject;
        ObjectId blockedObject;
        try {
            blockerObject = new ObjectId(blocker);
            blockedObject = new ObjectId(blocked);
        } catch (Exception e) {
            throw new BadRequestException("Invalid user id");
        }
        return blockService.isBlocked(blockerObject, blockedObject);
    }

    @GET
    @Path("/blocked_by/{blocker}")
    public boolean isBlockedBy(@HeaderParam("X-user-id") String blocked, @PathParam("blocker") String blocker) {
        if (blocker == null || blocker.isEmpty()) {
            throw new BadRequestException("X-user-id header is missing");
        }
        ObjectId blockerObject;
        ObjectId blockedObject;
        try {
            blockerObject = new ObjectId(blocker);
            blockedObject = new ObjectId(blocked);
        } catch (Exception e) {
            throw new BadRequestException("Invalid user id");
        }
        return blockService.isBlocked(blockerObject, blockedObject);
    }

    @GET
    @Path("/blocked_list/{blocker}")
    public List<String> getBlockedList(@PathParam("blocker") String blocker) {
        if (blocker == null || blocker.isEmpty()) {
            throw new BadRequestException("X-user-id header is missing");
        }
        ObjectId blockerObject;
        try {
            blockerObject = new ObjectId(blocker);
        } catch (Exception e) {
            throw new BadRequestException("Invalid user id");
        }
        return blockService.getBlockedList(blockerObject);
    }

    @GET
    @Path("/blocked_by_list/{blocked}")
    public List<String> getUsersWhoBlocked(@PathParam("blocked") String blocked) {
        if (blocked == null || blocked.isEmpty()) {
            throw new BadRequestException("X-user-id header is missing");
        }
        ObjectId blockedObject;
        try {
            blockedObject = new ObjectId(blocked);
        } catch (Exception e) {
            throw new BadRequestException("Invalid user id");
        }
        return blockService.getUsersWhoBlocked(blockedObject);
    }
}
