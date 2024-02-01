package com.epita.tinyx.gateway.service;

import com.epita.tinyxlib.entities.User;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {
    public List<User> getUsers() {
        return User.findAll().list();
    }

    public User getUserByUsername(String username) {
        return User.findUserByUsername(username);
    }

    public void createUser(User user) {
        user.persist();
    }

    public void updateUser(User user) {
        User existingUser = getUserByUsername(user.username);
        if (existingUser != null) {
            existingUser.username = user.username;
            existingUser.role = user.role;
        }
    }

    public void deleteUserByUsername(String username) {
        User existingUser = getUserByUsername(username);
        if (existingUser != null) {
            existingUser.delete();
        }
    }

    public User getUserByUsernameAndRole(String username, String role) {
        return User.findUserByUsernameAndRole(username, role);
    }

    public User getUserById(String id) {
        System.out.println("id: " + id);
        return User.findUserById(id);
    }
}
