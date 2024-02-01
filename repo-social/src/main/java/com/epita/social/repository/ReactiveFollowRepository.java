package com.epita.social.repository;

import com.epita.tinyxlib.entities.Follow;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

@ApplicationScoped
public class ReactiveFollowRepository implements ReactivePanacheMongoRepository<Follow> {
    public void deleteFollow(ObjectId follower, ObjectId followed) {
        delete("follower = ?1 and followed = ?2", follower, followed).subscribe().with(
                success -> System.out.println("Deleted follow"),
                failure -> System.out.println("Failed to delete follow"));
    }
}
