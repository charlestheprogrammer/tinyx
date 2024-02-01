package com.epita.user_timeline.subscriber;

import com.epita.tinyxlib.dto.LikeDTO;
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
public class LikedPostsSubscriber implements Consumer<LikeDTO> {
    private final PubSubCommands.RedisSubscriber subscriber;

    private final TimelineService timelineService;

    @Inject
    Driver neo4jDriver;

    public LikedPostsSubscriber(final RedisDataSource ds, final TimelineService timelineService) {
        subscriber = ds.pubsub(LikeDTO.class).subscribe("likes", this);
        this.timelineService = timelineService;
    }

    @Override
    public void accept(final LikeDTO message) {
        System.out.println("Received message: " + message.postId);
        final var session = neo4jDriver.session();
        session.executeWrite(tx -> tx
                .run("MERGE (u:User {id: \"" + message.userId + "\"}) " +
                        "MERGE (p:Post {id: \"" + message.postId + "\"}) " +
                        "MERGE (u)-[:LIKES {date: datetime()}]->(p)")
                .consume()
                .counters()
                .relationshipsCreated()
        );
        session.close();
        timelineService.createTimeline(message.userId);
    }

    @PreDestroy
    public void terminate() {
        subscriber.unsubscribe();
    }
}
