package com.epita.user_timeline.subscriber;


import com.epita.user_timeline.controller.dto.PostDTO;
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
public class NewPostsSubscriber implements Consumer<PostDTO> {

    private final PubSubCommands.RedisSubscriber subscriber;

    private final TimelineService timelineService;

    @Inject
    Driver neo4jDriver;

    public NewPostsSubscriber(final RedisDataSource ds, final TimelineService timelineService) {
        subscriber = ds.pubsub(PostDTO.class).subscribe("new-posts", this);
        this.timelineService = timelineService;
    }

    @Override
    public void accept(final PostDTO message) {
        final var session = neo4jDriver.session();
        session.executeWrite(tx -> tx
                .run("CREATE (p:Post {id: \"" + message.id + "\", date: datetime()})") // execute cypher code
                .consume() // get the result summary
                .counters() // get the summary counters
                .relationshipsCreated() // get the counter of created relationships
        );
        session.executeWrite(tx -> tx
                .run("MERGE (u:User {id: \"" + message.author + "\"}) " +
                        "MERGE (p:Post {id: \"" + message.id + "\"}) " +
                        "MERGE (p)-[:WRITTEN_BY]->(u)")
                .consume()
                .counters()
                .relationshipsCreated()
        );
        if (message.replyTo != null) {
            session.executeWrite(tx -> tx
                    .run("MERGE (p:Post {id: \"" + message.id + "\"}) " +
                            "MERGE (r:Post {id: \"" + message.replyTo + "\"}) " +
                            "MERGE (p)-[:REPLY_TO]->(r)")
                    .consume()
                    .counters()
                    .relationshipsCreated()
            );
        }
        session.close();
        timelineService.createTimeline(new ObjectId(message.author));
    }

    @PreDestroy
    public void terminate() {
        subscriber.unsubscribe();
    }
}
