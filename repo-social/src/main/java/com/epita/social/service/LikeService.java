package com.epita.social.service;

import com.epita.social.entity.Like;
import com.epita.social.external.PostService;
import io.quarkus.security.ForbiddenException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import org.bson.types.ObjectId;

@ApplicationScoped
public class LikeService {
    private final PostService postService;

    private final BlockService blockService;

    public LikeService(PostService postService, BlockService blockService) {
        this.postService = postService;
        this.blockService = blockService;
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
    }
}
