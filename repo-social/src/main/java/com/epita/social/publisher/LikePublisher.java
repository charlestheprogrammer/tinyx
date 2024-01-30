package com.epita.social.publisher;

import com.epita.social.entity.Like;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LikePublisher {
    private final PubSubCommands<Like> publisher;

    @Inject
    public LikePublisher(final RedisDataSource ds) {
        this.publisher = ds.pubsub(Like.class);
    }

    public void publishLike(final Like message) {
        publisher.publish("likes", message);
    }

    public void publishUnlike(final Like message) {
        publisher.publish("unlikes", message);
    }
}
