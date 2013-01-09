package com.tricode.checkin.service.impl;

import com.tricode.checkin.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Map;

@Component
public class InMemoryTestPersonsLoader {

    @Autowired(required = false)
    private InMemoryPersonService personService;

    @PostConstruct
    private void init() {
        if (personService != null) {
            final Field personsField = ReflectionUtils.findField(InMemoryPersonService.class, "persons");
            if (!personsField.isAccessible()) {
                personsField.setAccessible(true);
            }
            final Map<Integer, Person> personsMap = (Map<Integer, Person>) ReflectionUtils.getField(personsField, personService);

            personsMap.put(1, new Person(1, "Amployee", "1"));
            personsMap.put(2, new Person(2, "Bmployee", "2"));
            personsMap.put(3, new Person(3, "Cmployee", "3"));
            personsMap.put(4, new Person(4, "Dmployee", "4"));
            personsMap.put(5, new Person(5, "Employee", "5"));
            personsMap.put(6, new Person(6, "Fmployee", "6"));
            personsMap.put(7, new Person(7, "Gmployee", "7"));
            personsMap.put(8, new Person(8, "Hmployee", "8"));
            personsMap.put(9, new Person(9, "Imployee", "9"));
            personsMap.put(10, new Person(10, "Jmployee", "10"));
            personsMap.put(11, new Person(11, "Kmployee", "11"));
            personsMap.put(12, new Person(12, "Lmployee", "12"));
        }
    }

}
