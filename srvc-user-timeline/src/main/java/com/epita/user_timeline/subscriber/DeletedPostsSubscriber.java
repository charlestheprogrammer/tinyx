package com.epita.user_timeline.subscriber;

import com.epita.tinyxlib.dto.PostDTO;
import com.epita.user_timeline.service.TimelineService;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.neo4j.driver.Driver;

import java.util.function.Consumer;

@Startup
@ApplicationScoped
public class DeletedPostsSubscriber implements Consumer<PostDTO> {

    private final PubSubCommands.RedisSubscriber subscriber;

    private final TimelineService timelineService;

    @Inject
    Driver neo4jDriver;

    public DeletedPostsSubscriber(final RedisDataSource ds, final TimelineService timelineService) {
        subscriber = ds.pubsub(PostDTO.class).subscribe("delete-post", this);
        this.timelineService = timelineService;
    }

    @Override
    public void accept(final PostDTO message) {
        final var session = neo4jDriver.session();
        session.executeWrite(tx -> tx
                .run("MATCH (p:Post)-[:REPLY_TO]->(r:Post {id: \"" + message.id + "\"}) DETACH DELETE p")
                .consume()
                .counters()
                .nodesDeleted()
        );
        session.executeWrite(tx -> tx
                .run("MATCH (p:Post {id: \"" + message.id + "\"})-[r:WRITTEN_BY]->(u:User) DELETE r")
                .consume()
                .counters()
                .relationshipsDeleted()
        );
        session.executeWrite(tx -> tx
                .run("MATCH (u:User)-[r:LIKES]->(p:Post {id: \"" + message.id + "\"}) DELETE r")
                .consume()
                .counters()
                .relationshipsDeleted()
        );
        session.executeWrite(tx -> tx
                .run("MATCH (p:Post {id: \"" + message.id + "\"}) DETACH DELETE p")
                .consume()
                .counters()
                .nodesDeleted()
        );
        session.close();
        timelineService.createTimeline(new ObjectId(message.author));
    }

    @PreDestroy
    public void terminate() {
        subscriber.unsubscribe();
    }
}
