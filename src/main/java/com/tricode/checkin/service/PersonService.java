package com.tricode.checkin.service;

import com.tricode.checkin.model.Person;

import java.util.Collection;

public interface PersonService {

    Collection<Person> list();

    Person get(int id);

    Person getByExternalId(String externalId);

    Person save(Person person);

}
