package org.example.restfuldemo.mapper;

import org.example.restfuldemo.dto.response.task.TaskResponse;
import org.example.restfuldemo.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Interface for mapping Task entities to and from DTO objects.
 * Utilizes MapStruct framework for automated object mapping with custom configurations.
 */
@Mapper
public interface TaskMapper {
    /**
     * Singleton instance of the mapper created by MapStruct.
     */
    public TaskMapper mapper = Mappers.getMapper(TaskMapper.class);

    /**
     * Maps a Task entity to a TaskResponse DTO.
     * Custom mapping is defined to convert user entity ID to userId field in DTO.
     *
     * @param task Task entity to map
     * @return TaskResponse DTO representation of the entity
     */
    @Mapping(source = "user.id", target = "userId")
    public TaskResponse mapToResponse(Task task);
}
