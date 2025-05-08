package org.example.restfuldemo.dto.response;

import lombok.*;
import org.example.restfuldemo.entity.Person;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {
    private Long id;
    private String name;
    private List<TaskResponse> tasks; // Optional: Include tasks if needed

    // Static method to convert Entity to ResponseDTO
    //    public static PersonResponse fromEntity(Person person) {
    //        return PersonResponse.builder()
    //                .id(person.getId())
    //                .name(person.getName())
    //                .tasks(person.getTasks().stream()
    //                        .map(TaskResponse::fromEntity)
    //                        .collect(Collectors.toList()))
    //                .build();
    //    }
}
