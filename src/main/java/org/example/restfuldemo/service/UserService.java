package org.example.restfuldemo.service;

import org.example.restfuldemo.dto.request.UserRequest;
import org.example.restfuldemo.dto.response.user.UserResponse;

import java.util.List;

public interface UserService {
    /**
     * Creates a new user based on the provided request data.
     * Converts the request DTO to an entity, saves it to the database,
     * and returns the created user as a response DTO.
     *
     * @param userRequest DTO containing user creation data
     * @return UserResponse representing the newly created user
     */
    UserResponse createUser(UserRequest userRequest);

    /**
     * Retrieves all users from the repository and converts them to response DTOs.
     *
     * @return List of UserResponse objects representing all users in the system
     */
    List<UserResponse> getAllUsers();

    /**
     * Fetches a user by their unique identifier.
     *
     * @param id Unique identifier of the user to retrieve
     * @return UserResponse representing the requested user
     */
    UserResponse getUserById(Long id);

    /**
     * Updates an existing user's information.
     * Validates user existence before updating and handles database transactions.
     *
     * @param id          Unique identifier of the user to update
     * @param userRequest DTO containing updated user data
     */
    UserResponse updateUser(Long id, UserRequest userRequest);

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id Unique identifier of the user to delete
     */
    void deleteUser(Long id);
}
