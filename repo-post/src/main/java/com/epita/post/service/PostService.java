package com.epita.post.service;

import com.epita.post.entity.Post;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;

@ApplicationScoped
public class PostService {
    public void createPost(Post post) {
        post.persist();
    }

    public List<Post> getPosts() {
        return Post.findAll(Sort.descending("created_date")).list();
    }

    public List<Post> getPostsByAuthor(String author) {
        return Post.findPostsByAuthor(new ObjectId(author));
    }

    public Post getPostById(String id) {
        return Post.findPostById(id);
    }
}
