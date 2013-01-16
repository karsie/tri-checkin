package com.tricode.checkin.persistence;

import com.tricode.checkin.model.Person;

public interface PersonRepository extends Repository<Person> {

    Person getByExternalId(String externalId);

}
