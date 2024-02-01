package com.epita.home_timeline.subscriber;


import com.epita.tinyxlib.dto.PostDTO;
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
public class NewPostsSubscriber implements Consumer<PostDTO> {

    private final PubSubCommands.RedisSubscriber subscriber;

    private final SocialService socialService;

    private final TimelineService timelineService;

    public NewPostsSubscriber(final RedisDataSource ds, final SocialService socialService, final TimelineService timelineService) {
        subscriber = ds.pubsub(PostDTO.class).subscribe("new-posts", this);
        this.socialService = socialService;
        this.timelineService = timelineService;
    }

    @Override
    public void accept(final PostDTO message) {
        List<ObjectId> followers = socialService.getFollowers(new ObjectId(message.author));
        followers.forEach(timelineService::createTimeline);
    }

    @PreDestroy
    public void terminate() {
        subscriber.unsubscribe();
    }
}
