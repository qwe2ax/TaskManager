package com.example.services.interfaces;

import com.example.entities.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User getUserById(int id);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUserById(int id);
    User assignRoleToUser(int userId, int roleId);
    User assignTaskToUser(int userId, int taskId);

}
