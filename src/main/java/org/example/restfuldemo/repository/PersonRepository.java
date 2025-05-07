package org.example.restfuldemo.repository;

import org.example.restfuldemo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
