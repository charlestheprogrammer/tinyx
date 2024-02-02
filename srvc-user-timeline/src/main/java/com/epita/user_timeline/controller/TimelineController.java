package com.epita.user_timeline.controller;

import com.epita.tinyxlib.dto.TimelineDTO;
import com.epita.tinyxlib.entities.Timeline;
import com.epita.user_timeline.service.TimelineService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import org.bson.types.ObjectId;

import java.util.List;

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
    @Path("/{userId}")
    public TimelineDTO getTimelineByUserId(@PathParam("userId") final String userId) {
        return new TimelineDTO(timelineService.getTimeline(userId));
    }

    @GET
    @Path("")
    public TimelineDTO getUserTimeline(@HeaderParam("X-user-id") final String userId) {
        return new TimelineDTO(timelineService.getTimeline(userId));
    }
}
