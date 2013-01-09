package com.tricode.checkin.service;

import com.tricode.checkin.model.Person;

import java.util.Collection;

public interface PersonService {

    Collection<Person> list();

    Person get(Integer id);

    Person save(Person person);

}
