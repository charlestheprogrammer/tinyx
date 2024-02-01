package com.epita.social.service;

import com.epita.tinyxlib.entities.Follow;
import com.epita.social.publisher.FollowPublisher;
import com.epita.social.repository.ReactiveFollowRepository;
import com.epita.social.external.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class FollowService {
    private final UserService userService;

    private final ReactiveFollowRepository reactiveFollowRepository;

    private final BlockService blockService;

    private final FollowPublisher followPublisher;

    public FollowService(UserService userService, ReactiveFollowRepository reactiveFollowRepository, BlockService blockService, FollowPublisher followPublisher) {
        this.userService = userService;
        this.reactiveFollowRepository = reactiveFollowRepository;
        this.blockService = blockService;
        this.followPublisher = followPublisher;
    }
    public void follow(ObjectId follower, ObjectId followed) {
        if (!userService.userExists(follower) || !userService.userExists(followed)) {
            throw new BadRequestException("Invalid user id");
        }
        if (blockService.isBlocked(follower, followed)) {
            throw new BadRequestException("User is blocked");
        }
        Follow follow = new Follow(follower, followed);
        follow.persist();
        followPublisher.publishFollow(follow);
    }

    public void unfollow(ObjectId follower, ObjectId followed) {
        if (!userService.userExists(follower) || !userService.userExists(followed)) {
            throw new BadRequestException("Invalid user id");
        }
        if (blockService.isBlocked(follower, followed)) {
            throw new BadRequestException("User is blocked");
        }
        Follow follow = Follow.findFollow(follower, followed);
        if (follow != null) {
            followPublisher.publishUnfollow(follow);
            follow.delete();
        }
    }

    public boolean isFollowed(ObjectId follower, ObjectId followed) {
        if (!userService.userExists(follower) || !userService.userExists(followed)) {
            throw new BadRequestException("Invalid user id");
        }
        Follow follow = Follow.findFollow(follower, followed);
        return follow != null;
    }

    public List<Follow> getFollowers(ObjectId followed) {
        if (!userService.userExists(followed)) {
            throw new BadRequestException("Invalid user id");
        }
        return Follow.findFollows(followed);
    }

    public List<Follow> getFollowers(ObjectId followed, int limit, int offset) {
        if (!userService.userExists(followed)) {
            throw new BadRequestException("Invalid user id");
        }
        return Follow.findFollowedBy(followed, limit, offset);
    }

    public List<Follow> getFollows(ObjectId follower) {
        if (!userService.userExists(follower)) {
            throw new BadRequestException("Invalid user id");
        }
        return Follow.findFollowedBy(follower);
    }

    public List<Follow> getFollows(ObjectId follower, int limit, int offset) {
        if (!userService.userExists(follower)) {
            throw new BadRequestException("Invalid user id");
        }
        return Follow.findFollows(follower, limit, offset);
    }

    public void deleteFollowsBetweenUsers(ObjectId user1, ObjectId user2) {
        reactiveFollowRepository.deleteFollow(user1, user2);
        reactiveFollowRepository.deleteFollow(user2, user1);
        followPublisher.publishUnfollow(new Follow(user1, user2));
        followPublisher.publishUnfollow(new Follow(user2, user1));
    }
}
