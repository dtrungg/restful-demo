package org.example.restfuldemo.repository;

import org.example.restfuldemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for User domain objects.
 * Provides CRUD operations and custom query methods for user management.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     *
     * @param username The username to search for
     * @return An Optional containing the found User or empty if not found
     */
    public Optional<User> findByUserName(String username);

    /**
     * Checks if a user exists by their username.
     *
     * @param username The username to check
     * @return True if the user exists, false otherwise
     */
    public boolean existsByUserName(String username);
}
