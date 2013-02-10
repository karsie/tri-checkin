package com.tricode.checkin.web.impl;

import com.tricode.checkin.model.Person;
import com.tricode.checkin.service.PersonService;
import com.tricode.checkin.web.RestPersonInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@Controller
public class RestPersonController implements RestPersonInterface {

    private final PersonService personService;

    @Autowired
    public RestPersonController(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public Collection<Person> getPersons() {
        return personService.list();
    }

    @Override
    public Person getPerson(@PathVariable Integer id) {
        Assert.notNull(id, "Parameter 'id' can not be empty");

        return personService.get(id);
    }

    @Override
    public Person updatePerson(@PathVariable Integer id, @RequestBody Person person) {
        Assert.notNull(id, "Parameter 'id' can not be empty");
        Assert.notNull(person, "Parameter 'person' can not be empty");

        return personService.save(person);
    }
}
