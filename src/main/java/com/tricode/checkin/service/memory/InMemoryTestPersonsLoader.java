package com.tricode.checkin.service.memory;

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
            //noinspection unchecked
            final Map<Integer, Person> personsMap = (Map<Integer, Person>) ReflectionUtils.getField(personsField, personService);

            personsMap.put(1, Person.Builder.withId(1).withFirstName("Amployee").withLastName("1").get());
            personsMap.put(2, Person.Builder.withId(2).withFirstName("Bmployee").withLastName("2").get());
            personsMap.put(3, Person.Builder.withId(3).withFirstName("Cmployee").withLastName("3").get());
            personsMap.put(5, Person.Builder.withId(5).withFirstName("Employee").withLastName("5").get());
            personsMap.put(6, Person.Builder.withId(6).withFirstName("Fmployee").withLastName("6").get());
            personsMap.put(7, Person.Builder.withId(7).withFirstName("Gmployee").withLastName("7").get());
            personsMap.put(9, Person.Builder.withId(9).withFirstName("Imployee").withLastName("9").get());
            personsMap.put(10, Person.Builder.withId(10).withFirstName("Jmployee").withLastName("10").get());
            personsMap.put(11, Person.Builder.withId(11).withFirstName("Kmployee").withLastName("11").get());
            personsMap.put(12, Person.Builder.withId(12).withFirstName("Lmployee").withLastName("12").get());
        }
    }

}
