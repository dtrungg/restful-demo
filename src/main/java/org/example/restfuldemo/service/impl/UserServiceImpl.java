package org.example.restfuldemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.restfuldemo.config.CustomUserDetails;
import org.example.restfuldemo.config.JwtTokenProvider;
import org.example.restfuldemo.constants.Constants;
import org.example.restfuldemo.dto.request.UserLoginRequest;
import org.example.restfuldemo.dto.request.UserRequest;
import org.example.restfuldemo.dto.response.JwtAuthResponse;
import org.example.restfuldemo.dto.response.user.UserResponse;
import org.example.restfuldemo.entity.User;
import org.example.restfuldemo.exception.ResourceAlreadyExistException;
import org.example.restfuldemo.exception.ResourceNotFoundException;
import org.example.restfuldemo.mapper.UserMapper;
import org.example.restfuldemo.repository.UserRepository;
import org.example.restfuldemo.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the UserService interface providing CRUD operations for users.
 * Handles business logic related to user management, including creation, retrieval,
 * update, and deletion of users with proper error handling.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a new user based on the provided request data.
     * Converts the request DTO to an entity, saves it to the database,
     * and returns the created user as a response DTO.
     *
     * @param userRequest DTO containing user creation data
     * @return UserResponse representing the newly created user
     */
    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        // check if user already exists
        if (userRepository.existsByUserName(userRequest.getUserName())) {
            throw new ResourceAlreadyExistException(Constants.USER_EXIST);
        }
        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setPassWord(passwordEncoder.encode(userRequest.getPassWord()));
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(userRequest.getRole());

        User newUser = userRepository.save(user);
        UserResponse userResponse = UserMapper.mapper.mapToResponse(newUser);
        return userResponse;
    }

    /**
     * Retrieves all users from the repository and converts them to response DTOs.
     *
     * @return List of UserResponse objects representing all users in the system
     */
    @Override
    public List<UserResponse> getAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserResponse> userResponseList = userList.stream().map(UserMapper.mapper::mapToResponse).toList();
        return userResponseList;
    }

    /**
     * Fetches a user by their unique identifier.
     *
     * @param id Unique identifier of the user to retrieve
     * @return UserResponse representing the requested user
     * @throws ResourceNotFoundException if user with the given ID doesn't exist
     */
    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));
        UserResponse userResponse = UserMapper.mapper.mapToResponse(user);
        return userResponse;
    }

    /**
     * Updates an existing user's information.
     * Validates user existence before updating and handles database transactions.
     *
     * @param id          Unique identifier of the user to update
     * @param userRequest DTO containing updated user data
     * @throws ResourceNotFoundException if user with the given ID doesn't exist
     */
    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));
        user.setUserName(userRequest.getUserName());
        user.setPassWord(userRequest.getPassWord());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        User newUser = userRepository.save(user);
        return UserMapper.mapper.mapToResponse(newUser);
    }

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id Unique identifier of the user to delete
     * @throws ResourceNotFoundException if user with the given ID doesn't exist
     */
    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.USER_NOT_FOUND));
        userRepository.delete(user);
    }

    @Override
    public JwtAuthResponse login(UserLoginRequest userLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginRequest.getUserName(),
                userLoginRequest.getPassWord()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        List<String> roles = userDetails.getAuthorities().stream()
//                .map(item -> item.getAuthority())
//                .collect(Collectors.toList());

        return new JwtAuthResponse(token);
    }


}
