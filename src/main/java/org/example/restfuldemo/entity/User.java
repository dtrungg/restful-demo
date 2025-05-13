package org.example.restfuldemo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String passWord;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();
}
