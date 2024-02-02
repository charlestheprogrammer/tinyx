package com.epita.search.subscriber;

import com.epita.search.service.SearchService;
import com.epita.tinyxlib.dto.PostDTO;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.function.Consumer;

@Startup
@ApplicationScoped
public class CreatePostSubscriber implements Consumer<PostDTO> {

    private final PubSubCommands.RedisSubscriber subscriber;

    @Inject
    private SearchService searchService;

    public CreatePostSubscriber(final RedisDataSource ds) {
        this.subscriber = ds.pubsub(PostDTO.class).subscribe("new-posts", this);
    }

    @Override
    public void accept(final PostDTO message) {
        this.searchService.indexPost(message);
    }

    @PreDestroy
    public void terminate() {
        this.subscriber.unsubscribe();
    }
}
