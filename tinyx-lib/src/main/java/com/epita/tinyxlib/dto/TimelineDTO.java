package com.epita.tinyxlib.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class TimelineDTO {
    private UUID _id;
    private LocalDateTime fetchedAt;
    private List<PostDTO> postDTOS;
    private UserDTO specificUser;  // the user for whom the timeline was fetched (using their relations)
}
