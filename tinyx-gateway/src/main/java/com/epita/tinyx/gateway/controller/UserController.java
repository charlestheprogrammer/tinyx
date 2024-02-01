package com.epita.tinyx.gateway.controller;

import com.epita.tinyxlib.dto.UserDTO;
import com.epita.tinyxlib.entities.User;
import com.epita.tinyx.gateway.service.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;

import java.util.List;

@RequestScoped
@Path("/api/")
@Produces("application/json")
@Consumes("application/json")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GET
    @Path("/users")
    public List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(UserDTO::new).toList();
    }

    @POST
    @Path("/user")
    public void createUser(UserDTO userDTO) {
        userService.createUser(userDTO.toEntity());
    }

    @GET
    @Path("/user/by_username/{username}")
    public UserDTO getUserByUsername(String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return new UserDTO(userService.getUserByUsername(username));
    }

    @GET
    @Path("/user/by_user_id/{userId}")
    public UserDTO getUserById(String userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return new UserDTO(userService.getUserById(userId));
    }
}
