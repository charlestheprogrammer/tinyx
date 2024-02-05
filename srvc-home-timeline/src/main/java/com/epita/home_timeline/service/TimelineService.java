package com.epita.home_timeline.service;

import com.epita.tinyxlib.dto.TimelineItemDTO;
import com.epita.tinyxlib.entities.Timeline;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.neo4j.driver.Driver;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TimelineService {
    @Inject
    Driver neo4jDriver;

    public void createTimeline(final ObjectId userId) {
        final var session = neo4jDriver.session();
        final var foundNode = session.executeRead(tx -> tx
                .run("CALL {\n" +
                        "    MATCH (u:User {id: \"" + userId + "\"})-[:FOLLOWS]->(f:User)<-[:WRITTEN_BY]-(p:Post) RETURN p.id as id, p.date as date, null as userWhoLiked\n" +
                        "    UNION ALL\n" +
                        "    MATCH (u:User {id: \"" + userId + "\"})-[:FOLLOWS]->(f:User)-[l:LIKES]->(p:Post) RETURN p.id as id, l.date as date, f.id as userWhoLiked\n" +
                        "}\n" +
                        "RETURN id, userWhoLiked\n" +
                        "ORDER BY date DESC")
                .list());
        List<TimelineItemDTO> timeline = new ArrayList<>();
        foundNode.forEach(record -> {
            final var postId = record.get("id").asString();
            final var userWhoLike = record.get("userWhoLiked");
            timeline.add(new TimelineItemDTO(postId, userWhoLike.asString()));
        });
        Timeline existingTimeline = Timeline.findByUserId(userId);
        if (existingTimeline != null) {
            existingTimeline.posts = timeline;
            existingTimeline.update();
            session.close();
            return;
        }
        Timeline timelineEntity = new Timeline(timeline, userId);
        timelineEntity.persist();
        session.close();
    }

    public Timeline getTimeline(String userId) {
        Timeline existingTimeline = Timeline.findByUserId(new ObjectId(userId));
        if (existingTimeline == null) {
            createTimeline(new ObjectId(userId));
            existingTimeline = Timeline.findByUserId(new ObjectId(userId));
        }
        return existingTimeline;
    }

    public void refreshAllTimelines() {
        final var session = neo4jDriver.session();
        final var foundNode = session.executeRead(tx -> tx
                .run("MATCH (u:User) RETURN u.id as id")
                .list());
        foundNode.forEach(record -> {
            final var userId = record.get("id").asString();
            createTimeline(new ObjectId(userId));
        });
        session.close();
    }
}
