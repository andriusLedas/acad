package com.example.demo.controller;

import com.example.demo.model.domain.Person;
import com.example.demo.service.PersonService;
import com.example.demo.model.request.PersonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/getAll")
    public List<Person> getPersons() {
        return personService.getPersons();
    }

    @GetMapping(value = "/get/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return personService.fetchPersonById(id);
    }

    @PostMapping(value = "/add")
    public Person savePerson(@Validated @RequestBody PersonRequest personRequest) {
        return personService.createPerson(personRequest);
    }

    @GetMapping(value = "/getByName")
    public List<Person> getByFirstName(@RequestParam(name = "firstName") String firstName)
    {
        return personService.findByFirstName(firstName);
    }





}
