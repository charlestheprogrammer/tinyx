package com.epita.home_timeline.subscriber;

import com.epita.home_timeline.controller.dto.FollowDTO;
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
public class UnfollowsSubscriber implements Consumer<FollowDTO> {
    private final PubSubCommands.RedisSubscriber subscriber;

    private final TimelineService timelineService;

    @Inject
    Driver neo4jDriver;

    public UnfollowsSubscriber(final RedisDataSource ds, final TimelineService timelineService) {
        subscriber = ds.pubsub(FollowDTO.class).subscribe("unfollows", this);
        this.timelineService = timelineService;
    }

    @Override
    public void accept(final FollowDTO message) {
        final var session = neo4jDriver.session();
        session.executeWrite(tx -> tx
                .run("MATCH (follower:User {id: \"" + message.followerId + "\"})-[r:FOLLOWS]->(following:User {id: \"" + message.followingId + "\"}) DELETE r")
                .consume()
                .counters()
                .relationshipsDeleted()
        );
        session.close();
        timelineService.createTimeline(message.followerId);
    }

    @PreDestroy
    public void terminate() {
        subscriber.unsubscribe();
    }
}
