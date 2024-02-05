package com.epita.home_timeline.controller;

import com.epita.tinyxlib.dto.TimelineDTO;
import com.epita.home_timeline.service.TimelineService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Produces("application/json")
@Consumes("application/json")
@Path("/api/timeline")
public class TimelineController {
    private final TimelineService timelineService;

    public TimelineController(final TimelineService timelineService) {
        this.timelineService = timelineService;
    }

    @GET
    public TimelineDTO getUserTimeline(@HeaderParam("X-user-id") final String userId) {
        return new TimelineDTO(timelineService.getTimeline(userId));
    }

    @GET
    @Path("/refresh")
    public Response refreshTimelines() {
        timelineService.refreshAllTimelines();
        return Response.ok().build();
    }
}
