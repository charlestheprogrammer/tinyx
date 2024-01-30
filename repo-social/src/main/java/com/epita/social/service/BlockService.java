package com.epita.social.service;

import com.epita.social.entity.Block;
import com.epita.social.external.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import org.bson.types.ObjectId;

@ApplicationScoped
public class BlockService {
    private UserService userService;

    private final FollowService followService;
    public BlockService(UserService userService, FollowService followService) {
        this.userService = userService;
        this.followService = followService;
    }

    public void block(ObjectId blocker, ObjectId blocked) {
        if (!userService.userExists(blocker) || !userService.userExists(blocked)) {
            throw new BadRequestException("Invalid user id");
        }
        Block block = Block.findBlock(blocker, blocked);
        if (block == null) {
            block = new Block(blocker, blocked);
            block.persist();
        } else {
            block.isActive = true;
            block.date = java.time.LocalDateTime.now();
            block.update();
        }
        followService.deleteFollowsBetweenUsers(blocker, blocked);
    }

    public void unblock(ObjectId blocker, ObjectId blocked) {
        if (!userService.userExists(blocker) || !userService.userExists(blocked)) {
            throw new BadRequestException("Invalid user id");
        }
        Block block = Block.findBlock(blocker, blocked);
        if (block != null) {
            block.isActive = false;
            block.date = java.time.LocalDateTime.now();
            block.update();
        }
    }

    public boolean isBlocked(ObjectId blocker, ObjectId blocked) {
        if (!userService.userExists(blocker) || !userService.userExists(blocked)) {
            throw new BadRequestException("Invalid user id");
        }
        Block block = Block.findActiveBlock(blocker, blocked);
        return block != null;
    }
}
