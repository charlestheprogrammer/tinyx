package com.epita.post.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.panache.common.Sort;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

public class Post extends PanacheMongoEntity {
    public String text;

    public ObjectId author;

    public ObjectId repost;

    public String media;

    public LocalDateTime created_date;

    public Post() {
    }

    public Post(String text, ObjectId author, ObjectId repost, String media) {
        if (text == null && media == null && repost == null) {
            throw new IllegalArgumentException("Post must contain at least one of text, media, repost");
        }
        if (text != null && media != null && repost != null) {
            throw new IllegalArgumentException("Post can contain at most two of text, media, repost");
        }
        this.text = text;
        this.author = author;
        this.repost = repost;
        this.media = media;
        this.created_date = LocalDateTime.now();
    }

    public static Post findPostById(String id) {
        return find("_id", new ObjectId(id)).firstResult();
    }

    public static Post findPostById(ObjectId id) {
        return find("_id", id).firstResult();
    }

    public static List<Post> findPostsByAuthor(ObjectId author) {
        return find("author", Sort.descending("created_date"), author).list();
    }

    public static List<Post> findPostsByAuthor(ObjectId author, int limit, int offset) {
        return find("author", Sort.descending("created_date"), author).page(offset, limit).list();
    }

    public static Post findPostByTitle(String title) {
        return find("title", title).firstResult();
    }

    public static Post findPostByContent(String content) {
        return find("content", content).firstResult();
    }
}
