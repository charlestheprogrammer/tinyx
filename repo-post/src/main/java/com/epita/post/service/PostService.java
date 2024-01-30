package com.epita.post.service;

import com.epita.post.entity.Post;
import com.epita.post.external.UserService;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
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
}
