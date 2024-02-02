package com.epita.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.epita.tinyxlib.dto.PostESDTO;
import com.epita.tinyxlib.dto.PostDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class SearchService {

    @Inject
    ElasticsearchClient client;

    public void indexPost(PostDTO post){
        PostESDTO postES = new PostESDTO(post);
        IndexRequest<PostESDTO> indexRequest = IndexRequest.of(x -> x.index("posts").id(postES.id).document(postES));
        try {
            client.index(indexRequest);
            System.out.println("Indexing document");
        }
        catch (IOException e) {
            // TODO: change print with a logger ?
            System.out.println("Error while indexing in ES: " + e.getMessage());
        }
    }

    public void deletePost(PostDTO post) {
        DeleteRequest deleteRequest = DeleteRequest.of(x -> x.index("posts").id(post.id));
        try {
            client.delete(deleteRequest);
            System.out.println("Deleting document");
        }
        catch (IOException e) {
            // TODO: change print with a logger ?
            System.out.println("Error while deleting in ES: " + e.getMessage());
        }
    }

    public List<PostESDTO> searchPosts(String query) {
        String queryWithoutHashtags = Arrays.stream(query.split("\\s+"))
                .filter(word -> !word.startsWith("#"))
                .toList().stream().reduce("", (acc, term) -> acc + " " + term);
        Query regularWordsInQuery = Query.of(queryBuilder -> queryBuilder
                .match(matchQuery -> matchQuery.field("text")
                        .query(queryWithoutHashtags)
                        .operator(Operator.Or))
        );
        List<String> hashtags = Arrays.stream(query.split("\\s+"))
                .filter(line -> line.startsWith("#"))
                .map(line -> line.substring(1))
                .toList();
        if (hashtags.isEmpty()) {
            return findWithoutHashtags(regularWordsInQuery);
        }
        Query hashtagsInQuery = Query.of(queryBuilder -> queryBuilder
                .termsSet(termsSetQuery -> termsSetQuery
                        .field("hashtags")
                        .terms(hashtags)
                        .minimumShouldMatchScript(script -> script.inline(scriptBuilder -> scriptBuilder
                                .source("params.num_terms")))
                ));
        try {
            final var request = SearchRequest.of(requestBuilder -> requestBuilder
                    .index("posts")
                    .query(queryBuilder -> queryBuilder
                            .bool(boolQueryBuilder -> boolQueryBuilder
                                    .must(hashtagsInQuery)
                                    .should(regularWordsInQuery)
                            )
                    )
            );
            final var response = client.search(request, PostESDTO.class);
            return response.hits().hits().stream().map(Hit::source).toList();
        } catch (IOException e) {
            System.out.println("Error while searching in ES: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<PostESDTO> findWithoutHashtags(Query query) {
        try {
            final var request = SearchRequest.of(requestBuilder -> requestBuilder
                    .index("posts")
                    .query(queryBuilder -> queryBuilder
                            .bool(boolQueryBuilder -> boolQueryBuilder
                                    .should(query)
                            )
                    )
            );
            final var response = client.search(request, PostESDTO.class);
            return response.hits().hits().stream().map(Hit::source).toList();
        } catch (IOException e) {
            System.out.println("Error while searching in ES: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
