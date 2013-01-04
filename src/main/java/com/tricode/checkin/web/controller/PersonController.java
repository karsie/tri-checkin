package com.tricode.checkin.web.controller;

import com.tricode.checkin.web.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static org.springframework.util.ObjectUtils.nullSafeEquals;

@Controller
@RequestMapping(value = "person")
public class PersonController {

    private static final List<Person> persons = Arrays.asList(
            new Person("001", "Amployee", "1"),
            new Person("002", "Bmployee", "2"),
            new Person("003", "Cmployee", "3"),
            new Person("004", "Dmployee", "4"),
            new Person("005", "Employee", "5"),
            new Person("006", "Fmployee", "6"),
            new Person("007", "Gmployee", "7"),
            new Person("008", "Hmployee", "8"),
            new Person("009", "Imployee", "9"),
            new Person("010", "Jmployee", "10"),
            new Person("011", "Kmployee", "11"),
            new Person("012", "Lmployee", "12")
    );

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Person> getPersons() {
        return persons;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public @ResponseBody Person getPerson(@PathVariable String id) {
        for (Person person : persons) {
            if (nullSafeEquals(id, person.getId())) {
                return person;
            }
        }
        return null;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void updatePerson(@RequestBody Person updatedPerson, HttpServletResponse response) {
        for (Person person : persons) {
            if (nullSafeEquals(updatedPerson.getId(), person.getId())) {
                person.setStatus(updatedPerson.getStatus());
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
}
