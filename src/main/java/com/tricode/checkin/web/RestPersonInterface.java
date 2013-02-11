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
    Collection<Person> list();

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    Person get(@PathVariable Integer id);

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    Person update(@PathVariable Integer id, @RequestBody Person person);
}
