package com.example.demo.service;

import com.example.demo.model.domain.Person;
import com.example.demo.model.request.PersonRequest;
import com.example.demo.model.response.PersonResponse;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getPersons(){
        return fetchPersons();
    }

    public Person fetchPersonById(Long id){
        Optional<Person> person = personRepository.findById(id);
        return person.get();
    }

    public Person createPerson(PersonRequest request) {
        Person person = convertPersonRequestToPerson(request);
        return personRepository.save(person);
    }

    public List<Person> findByFirstName(String name) {
        return personRepository.findAllByFirstName(name);
    }

    private List<Person> fetchPersons(){
        return personRepository.findAll();
    }

    private PersonResponse convertPersonToPersonResponse(Person person){
        return person == null
                ? null
                : new PersonResponse(person.getFirstName(), person.getLastName(), person.getEmail(), person.getPhone());
    }

    private Person convertPersonRequestToPerson(PersonRequest request){
        return request == null
                ? null
                : Person.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phone(request.phone())
                .build();
    }
}
