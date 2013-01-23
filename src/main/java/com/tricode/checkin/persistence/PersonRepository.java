package com.tricode.checkin.persistence;

import com.tricode.checkin.model.Person;

import java.util.Collection;

public interface PersonRepository extends Repository<Person> {

    Person get(int id);

    Person getByExternalId(String externalId);

    Collection<Person> findAll();
}
