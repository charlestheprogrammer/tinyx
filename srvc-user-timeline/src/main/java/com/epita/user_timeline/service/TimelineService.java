package com.epita.user_timeline.service;

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
                        "    MATCH (p:Post)-[:WRITTEN_BY]->(u:User {id: \"" + userId + "\"}) RETURN p.id as id, p.date as date\n" +
                        "    UNION\n" +
                        "    MATCH (u:User {id: \"" + userId + "\"})-[l:LIKES]->(p:Post) RETURN p.id as id, l.date as date\n" +
                        "}\n" +
                        "RETURN id\n" +
                        "ORDER BY date DESC"
                )
                .list());
        List<TimelineItemDTO> timeline = new ArrayList<>();
        foundNode.forEach(record -> {
            final var postId = record.get("id").asString();
            timeline.add(new TimelineItemDTO(postId, null));
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
        Timeline timeline = Timeline.findByUserId(new ObjectId(userId));
        if (timeline == null) {
            createTimeline(new ObjectId(userId));
            timeline = Timeline.findByUserId(new ObjectId(userId));
        }
        return timeline;
    }
}
