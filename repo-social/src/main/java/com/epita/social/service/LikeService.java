package com.epita.social.service;

import com.epita.social.entity.Like;
import com.epita.social.external.PostService;
import com.epita.social.publisher.LikePublisher;
import io.quarkus.security.ForbiddenException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class LikeService {
    private final PostService postService;

    private final BlockService blockService;

    private final LikePublisher likePublisher;

    public LikeService(PostService postService, BlockService blockService, LikePublisher likePublisher) {
        this.postService = postService;
        this.blockService = blockService;
        this.likePublisher = likePublisher;
    }

    public void createLike(ObjectId postId, ObjectId userId) {
        String postAuthorId = postService.getPostAuthor(postId);
        if (postAuthorId == null) {
            throw new BadRequestException("Post does not exist");
        }
        if (userId.equals(new ObjectId(postAuthorId))) {
            throw new BadRequestException("You cannot like your own post");
        }
        if (Like.findLike(postId, userId) != null) {
            throw new BadRequestException("You already liked this post");
        }
        if (blockService.isBlocked(userId, new ObjectId(postAuthorId)) || blockService.isBlocked(new ObjectId(postAuthorId), userId)) {
            throw new ForbiddenException("You are blocked by the author of this post");
        }
        Like like = new Like(postId, userId);
        like.persist();
        likePublisher.publishLike(like);
    }

    public void deleteLike(ObjectId postId, ObjectId userId) {
        Like like = Like.findLike(postId, userId);
        if (like == null) {
            throw new BadRequestException("You did not like this post");
        }
        like.delete();
        likePublisher.publishUnlike(like);
    }

    public List<String> getLikes(ObjectId postId) {
        return Like.findLikes(postId).stream().map(like -> like.userId.toString()).toList();
    }

    public List<String> postsLikedBy(ObjectId userId) {
        return Like.findLikesFromUser(userId).stream().map(like -> like.postId.toString()).toList();
    }
}
