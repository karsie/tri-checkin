package com.tricode.checkin.web.controller;

import com.tricode.checkin.model.Person;
import com.tricode.checkin.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    Collection<Person> getPersons() {
        return personService.list();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Person getPerson(@PathVariable Integer id) {
        return personService.get(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Person updatePerson(@RequestBody Person updatedPerson) {
        return personService.save(updatedPerson);
    }
}
