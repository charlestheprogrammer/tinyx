package com.epita.post.service;

import com.epita.post.entity.Post;
import com.epita.post.external.UserService;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class PostService {
    private final UserService userService;

    @Inject
    public PostService(UserService userService) {
        this.userService = userService;
    }
    public void createPost(Post post) {
        if (!userService.userExists(post.author))
            throw new BadRequestException("Author does not exist");
        if (post.repost != null && Post.findPostById(post.repost) == null)
            throw new BadRequestException("Repost does not exist");
        post.persist();
    }

    public void createReply(Post post) {
        if (!userService.userExists(post.author))
            throw new BadRequestException("Author does not exist");
        if (post.repost != null && Post.findPostById(post.repost) == null)
            throw new BadRequestException("Repost does not exist");
        if (post.replyTo != null && Post.findPostById(post.replyTo) == null)
            throw new BadRequestException("Reply does not exist");
        post.persist();
    }

    public List<Post> getPosts() {
        return Post.findAllPosts();
    }

    public List<Post> getPostsByAuthor(String author) {
        return Post.findPostsByAuthor(new ObjectId(author));
    }

    public Post getPostById(String id) {
        return Post.findPostById(id);
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
        post.delete();
    }
}
