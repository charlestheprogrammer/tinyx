package com.epita.home_timeline.service;

import com.epita.home_timeline.controller.dto.TimelineItemDTO;
import com.epita.home_timeline.entity.Timeline;
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
                .run("MATCH (u:User {id: \"" + userId + "\"})-[:FOLLOWS]->(f:User)<-[:WRITTEN_BY]-(p:Post) " +
                        "RETURN p.id AS id, null as userWhoLike " +
                        "UNION " +
                        "MATCH (u:User {id: \"" + userId + "\"})-[:FOLLOWS]->(f:User)-[l:LIKES]->(p:Post) " +
                        "RETURN p.id AS id, f.id as userWhoLike " +
                        "ORDER BY l.date, p.date DESC")
                .list());
        List<TimelineItemDTO> timeline = new ArrayList<>();
        foundNode.forEach(record -> {
            final var postId = record.get("id").asString();
            final var userWhoLike = record.get("userWhoLike");
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
        return Timeline.findByUserId(new ObjectId(userId));
    }
}
