package com.epita.post.publisher;

import com.epita.post.controller.dto.PostDTO;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PostsPublisher {
    private final PubSubCommands<PostDTO> publisher;

    @Inject
    public PostsPublisher(final RedisDataSource ds) {
        this.publisher = ds.pubsub(PostDTO.class);
    }

    public void publishNewPost(final PostDTO message) {
        publisher.publish("new-posts", message);
    }

    public void publishDeletedPost(final PostDTO message) {
        publisher.publish("delete-post", message);
    }
}
