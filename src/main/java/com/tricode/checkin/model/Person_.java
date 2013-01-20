package com.tricode.checkin.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Calendar;

@StaticMetamodel(Person.class)
public class Person_ {

    public static volatile SingularAttribute<Person, Integer> id;

    public static volatile SingularAttribute<Person, String> externalId;

    public static volatile SingularAttribute<Person, String> name;

    public static volatile SingularAttribute<Person, String> first;

    public static volatile SingularAttribute<Person, String> last;

    public static volatile SingularAttribute<Person, LocationStatus> status;

    public static volatile SingularAttribute<Person, Boolean> eatingIn;

    public static volatile SingularAttribute<Person, Calendar> birthDate;

    public static volatile SingularAttribute<Person, Calendar> startDate;
}
