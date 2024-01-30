package com.epita.post.service;

import com.epita.post.controller.dto.PostDTO;
import com.epita.post.entity.Post;
import com.epita.post.external.BlockService;
import com.epita.post.external.UserService;
import com.epita.post.publisher.PostsPublisher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class PostService {
    private final UserService userService;

    private final PostsPublisher postsPublisher;

    private final BlockService blockService;

    @Inject
    public PostService(UserService userService, PostsPublisher postsPublisher, BlockService blockService) {
        this.userService = userService;
        this.postsPublisher = postsPublisher;
        this.blockService = blockService;
    }
    public void createPost(Post post) {
        if (!userService.userExists(post.author))
            throw new BadRequestException("Author does not exist");
        Post repost = Post.findPostById(post.repost);
        if (post.repost != null && Post.findPostById(post.repost) == null)
            throw new BadRequestException("Repost does not exist");
        if (blockService.isBlocked(post.author.toString(), repost.author.toString()) || blockService.isBlocked(repost.author.toString(), post.author.toString()))
            throw new ForbiddenException("You are blocked by the author of this post");
        post.persist();
        postsPublisher.publishNewPost(new PostDTO(post));
    }

    public void createReply(Post post) {
        if (!userService.userExists(post.author))
            throw new BadRequestException("Author does not exist");
        Post repost = Post.findPostById(post.repost);
        if (post.repost != null && Post.findPostById(post.repost) == null)
            throw new BadRequestException("Repost does not exist");
        if (blockService.isBlocked(post.author.toString(), repost.author.toString()) || blockService.isBlocked(repost.author.toString(), post.author.toString()))
            throw new ForbiddenException("You are blocked by the author of this post");
        Post replyTo = Post.findPostById(post.replyTo);
        if (post.replyTo != null && Post.findPostById(post.replyTo) == null)
            throw new BadRequestException("Reply does not exist");
        if (blockService.isBlocked(post.author.toString(), replyTo.author.toString()) || blockService.isBlocked(replyTo.author.toString(), post.author.toString()))
            throw new ForbiddenException("You are blocked by the author of this post");

        post.persist();
        postsPublisher.publishNewPost(new PostDTO(post));
    }

    public List<Post> getPosts() {
        return Post.findAllPosts();
    }

    public List<Post> getPostsByAuthor(String author) {
        return Post.findPostsByAuthor(new ObjectId(author));
    }

    public Post getPostById(String id) {
        Post post = Post.findPostById(id);
        if (post == null)
            throw new NotFoundException("Post does not exist");
        return post;
    }

    public List<Post> getReplies(String postId, int limit, int offset) {
        if (Post.findPostById(postId) == null)
            throw new BadRequestException("Post does not exist");
        return Post.findRepliesToPost(postId, limit, offset);
    }

    public void deletePost(String id, String userId) {
        try {
            if (!userService.userExists(new ObjectId(userId)))
                throw new BadRequestException("Author does not exist");
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Author does not exist");
        }
        Post post = Post.findPostById(id);
        if (post == null)
            throw new BadRequestException("Post does not exist");
        if (!post.author.toString().equals(userId))
            throw new ForbiddenException("You are not the author of this post");
        postsPublisher.publishDeletedPost(new PostDTO(post));
        post.delete();
    }
}
