package com.epita.search.controller;

import com.epita.tinyxlib.dto.PostESDTO;
import com.epita.search.service.SearchService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;

import java.util.List;

@RequestScoped
@Path("/api/")
@Produces("application/json")
public class SearchController {

    private final SearchService searchService;

    public SearchController(final SearchService searchService) {
        this.searchService = searchService;
    }

    @POST
    @Path("posts/search")
    @Produces("application/json")
    @Consumes("plain/text")
    public List<String> search(String searchTerms) {
        // TODO: to finish
        return searchService.searchPosts(searchTerms).stream().map(PostESDTO::getId).toList();
    }
}
