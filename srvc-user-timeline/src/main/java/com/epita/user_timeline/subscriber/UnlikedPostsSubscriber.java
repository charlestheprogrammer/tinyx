package com.epita.user_timeline.subscriber;

import com.epita.user_timeline.controller.dto.LikeDTO;
import com.epita.user_timeline.service.TimelineService;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.neo4j.driver.Driver;

import java.util.function.Consumer;

@Startup
@ApplicationScoped
public class UnlikedPostsSubscriber implements Consumer<LikeDTO> {
    private final PubSubCommands.RedisSubscriber subscriber;

    private final TimelineService timelineService;

    @Inject
    Driver neo4jDriver;

    public UnlikedPostsSubscriber(final RedisDataSource ds, final TimelineService timelineService) {
        subscriber = ds.pubsub(LikeDTO.class).subscribe("unlikes", this);
        this.timelineService = timelineService;
    }

    @Override
    public void accept(final LikeDTO message) {
        System.out.println("Received message: " + message.postId);
        final var session = neo4jDriver.session();
        session.executeWrite(tx -> tx
                .run("MATCH (u:User {id: \"" + message.userId + "\"})-[l:LIKES]->(p:Post {id: \"" + message.postId + "\"}) " +
                        "DELETE l")
                .consume()
                .counters()
                .relationshipsDeleted()
        );
        session.close();
        timelineService.createTimeline(message.userId);
    }

    @PreDestroy
    public void terminate() {
        subscriber.unsubscribe();
    }
}
