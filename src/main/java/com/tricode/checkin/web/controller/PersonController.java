package com.tricode.checkin.web.controller;

import com.tricode.checkin.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "person")
public class PersonController {

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody Person getPerson(@PathVariable String id) {
        return new Person(id, "name");
    }
}
