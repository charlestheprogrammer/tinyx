package com.epita.social.publisher;

import com.epita.social.entity.Follow;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class FollowPublisher {
    private final PubSubCommands<Follow> publisher;

    @Inject
    public FollowPublisher(final RedisDataSource ds) {
        this.publisher = ds.pubsub(Follow.class);
    }

    public void publishFollow(final Follow message) {
        publisher.publish("follows", message);
    }

    public void publishUnfollow(final Follow message) {
        publisher.publish("unfollows", message);
    }
}
