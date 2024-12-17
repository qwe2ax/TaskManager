package com.example.services.implementations;

import com.example.dao.RoleRepository;
import com.example.dao.TaskRepository;
import com.example.dao.UserRepository;
import com.example.entities.Role;
import com.example.entities.Task;
import com.example.entities.User;
import com.example.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TaskRepository taskRepository;

    @Transactional
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User assignRoleToUser(int userId, int roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No such user by id " + userId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("No such role by id " + roleId));

        if (!user.getRoles().contains(role)) {
            user.getRoles().add(role);
            userRepository.save(user);
        } else {
            throw new IllegalStateException("User already has role " + role.getName());
        }

        return user;
    }

    @Override
    public User assignTaskToUser(int userId, int taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No such user by id " + userId));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("No such task by id " + taskId));

        if (!user.getTasks().contains(task)) {
            user.getTasks().add(task);
            userRepository.save(user);
        } else {
            throw new IllegalStateException("User already has task " + task.getId());
        }

        return user;
    }

}
