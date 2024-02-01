package com.epita.home_timeline.subscriber;


import com.epita.tinyxlib.dto.LikeDTO;
import com.epita.home_timeline.external.SocialService;
import com.epita.home_timeline.service.TimelineService;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.function.Consumer;

@Startup
@ApplicationScoped
public class LikedPostsSubscriber implements Consumer<LikeDTO> {

    private final PubSubCommands.RedisSubscriber subscriber;

    private final SocialService socialService;

    private final TimelineService timelineService;

    public LikedPostsSubscriber(final RedisDataSource ds, final SocialService socialService, final TimelineService timelineService) {
        subscriber = ds.pubsub(LikeDTO.class).subscribe("likes", this);
        this.socialService = socialService;
        this.timelineService = timelineService;
    }

    @Override
    public void accept(final LikeDTO message) {
        List<ObjectId> followers = socialService.getFollowers(message.userId);
        followers.forEach(timelineService::createTimeline);
    }

    @PreDestroy
    public void terminate() {
        subscriber.unsubscribe();
    }
}
