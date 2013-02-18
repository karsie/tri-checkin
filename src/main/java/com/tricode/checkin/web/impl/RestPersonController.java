package com.tricode.checkin.web.impl;

import com.tricode.checkin.model.Person;
import com.tricode.checkin.service.PersonService;
import com.tricode.checkin.web.RestPersonInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

@Controller
public class RestPersonController implements RestPersonInterface {

    private static final Logger log = LoggerFactory.getLogger(RestPersonController.class);

    private final PersonService personService;

    @Autowired
    public RestPersonController(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public Collection<Person> list() {
        return personService.list();
    }

    @Override
    public Person get(@PathVariable Integer id) {
        Assert.notNull(id, "Parameter 'id' can not be empty");

        return personService.get(id);
    }

    @Override
    public Person update(@PathVariable Integer id, @RequestBody Person person) {
        Assert.notNull(id, "Parameter 'id' can not be empty");
        Assert.notNull(person, "Parameter 'person' can not be empty");

        return personService.save(person);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void exception(Exception e) {
        log.error("error in controller", e);
    }
}
