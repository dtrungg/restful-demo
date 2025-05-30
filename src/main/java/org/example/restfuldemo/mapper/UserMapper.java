package org.example.restfuldemo.mapper;

import org.example.restfuldemo.dto.response.user.UserResponse;
import org.example.restfuldemo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Interface for mapping User entities to and from DTO objects.
 * Utilizes MapStruct framework for automated object mapping.
 */
@Mapper
public interface UserMapper {
    /**
     * Singleton instance of the mapper created by MapStruct.
     */
    public UserMapper mapper = Mappers.getMapper(UserMapper.class);

    /**
     * Maps a User entity to a UserResponse DTO.
     *
     * @param user User entity to map
     * @return UserResponse DTO representation of the entity
     */
    public UserResponse mapToResponse(User user);
}
