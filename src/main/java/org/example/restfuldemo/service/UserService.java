package org.example.restfuldemo.service;

import org.example.restfuldemo.dto.request.UserRequest;
import org.example.restfuldemo.dto.response.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    void updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);
}
