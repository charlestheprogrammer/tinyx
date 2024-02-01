package com.epita.home_timeline.subscriber;

import com.epita.tinyxlib.dto.FollowDTO;
import com.epita.home_timeline.service.TimelineService;
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
public class FollowsSubscriber implements Consumer<FollowDTO> {

    private final PubSubCommands.RedisSubscriber subscriber;

    private final TimelineService timelineService;

    @Inject
    Driver neo4jDriver;

    public FollowsSubscriber(final RedisDataSource ds, final TimelineService timelineService) {
        subscriber = ds.pubsub(FollowDTO.class).subscribe("follows", this);
        this.timelineService = timelineService;
    }

    @Override
    public void accept(final FollowDTO message) {
        System.out.println("follower: " + message.followerId + " following: " + message.followingId);
        final var session = neo4jDriver.session();
        session.executeWrite(tx -> tx
                .run("MERGE (follower:User {id: \"" + message.followerId + "\"}) MERGE (following:User {id: \"" + message.followingId + "\"}) MERGE (follower)-[:FOLLOWS]->(following)")
                .consume()
                .counters()
                .relationshipsCreated()
        );
        session.close();
        timelineService.createTimeline(message.followerId);
    }

    @PreDestroy
    public void terminate() {
        subscriber.unsubscribe();
    }
}
