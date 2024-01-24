package com.epita.tinyxlib.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection="Posts")
public class Post extends PanacheMongoEntity {
    private ObjectId authorId;
    private LocalDateTime postedAt;
    private String text;
    private String media;
    private ObjectId quotedPostId;
    private ObjectId parentPostId;

    public static List<Post> findAllPosts() {
        return findAll().list();
    }

    public static Post findByID(ObjectId id) {
        return find("id", id).firstResult();
    }

    public static Post findByAuthorId(ObjectId authorId) {
        return find("authorId", authorId).firstResult();
    }

    public static List<Post> findByParentPostId(ObjectId parentPostId) {
        return find("parentPostId", parentPostId).list();
    }

    public static List<Post> findByQuotedPostId(ObjectId quotedPostId) {
        return find("quotedPostId", quotedPostId).list();
    }

    public static List<Post> findPostedAfter(LocalDateTime postedAfter) {
        return find("postedAt > ?1", postedAfter).list();
    }
}
