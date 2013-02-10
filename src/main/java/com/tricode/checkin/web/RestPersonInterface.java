package com.tricode.checkin.web;

import com.tricode.checkin.model.Person;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@RequestMapping("rest/person")
public interface RestPersonInterface {

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    Collection<Person> getPersons();

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    Person getPerson(@PathVariable Integer id);

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    Person updatePerson(@PathVariable Integer id, @RequestBody Person person);
}
