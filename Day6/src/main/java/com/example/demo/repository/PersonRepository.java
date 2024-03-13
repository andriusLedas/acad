package com.example.demo.repository;

import com.example.demo.model.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findAllByFirstName(String firstName);
    List<Person> findAllByLastName(String lastName);
}
