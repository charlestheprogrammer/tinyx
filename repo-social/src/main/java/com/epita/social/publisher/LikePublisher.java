package com.epita.social.publisher;

import com.epita.social.controller.dto.LikeDTO;
import com.epita.social.entity.Like;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LikePublisher {
    private final PubSubCommands<LikeDTO> publisher;

    @Inject
    public LikePublisher(final RedisDataSource ds) {
        this.publisher = ds.pubsub(LikeDTO.class);
    }

    public void publishLike(final Like message) {
        publisher.publish("likes", new LikeDTO(message));
    }

    public void publishUnlike(final Like message) {
        publisher.publish("unlikes", new LikeDTO(message));
    }
}
