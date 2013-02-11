package com.tricode.checkin.task;

import com.tricode.checkin.config.CheckinConfig;
import com.tricode.checkin.model.Person;
import com.tricode.checkin.service.PersonService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class ReloadEmployeesXmlDailyTaskTest {

    private static final Logger log = LoggerFactory.getLogger(ReloadEmployeesXmlDailyTaskTest.class);

    private ReloadEmployeesXmlDailyTask task;

    @Mock
    private PersonService personService;

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<Object[]> getParameters() {
        return Arrays.asList(new Object[][]{
                { "valid-utf8", new ValidUtf8Config() },
                { "valid-ansi", new ValidAnsiConfig() },
                { "invalid", new InvalidXmlConfig() },
                { "inexistent", new FileNotFoundConfig() }
        });
    }

    @Parameterized.Parameter(0)
    public String testName;

    @Parameterized.Parameter(1)
    public CheckinConfig checkinConfig;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        task = new ReloadEmployeesXmlDailyTask(checkinConfig, personService);
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(500); // allow failure scenarios to run 3 times

        ReflectionTestUtils.invokeMethod(task, "destroy");
    }

    @Test
    public void testRunTaskWhenNoPersonsThenCreateNewPersons() {
        when(personService.getByExternalId(anyString())).thenReturn(null);
        when(personService.save(any(Person.class))).thenAnswer(new PersonCreatedAnswer());

        task.runTask();

        if (testName.startsWith("valid")) {
            verify(personService, times(8)).save(any(Person.class));
        } else {
            verify(personService, never()).save(any(Person.class));
        }
    }

    @Test
    public void testRunTaskWhenPersonsExistThenNameIsUpdated() {
        final Person person1 = Person.Builder.withId(1).withFirstName("a").get();
        final Person[] persons2to8 = {
                Person.Builder.withId(2).withFirstName("b").get(),
                Person.Builder.withId(3).withFirstName("c").get(),
                Person.Builder.withId(4).withFirstName("d").get(),
                Person.Builder.withId(5).withFirstName("e").get(),
                Person.Builder.withId(6).withFirstName("f").get(),
                Person.Builder.withId(7).withFirstName("g").get(),
                Person.Builder.withId(8).withFirstName("h").get()
        };

        when(personService.getByExternalId(anyString())).thenReturn(person1, persons2to8);
        when(personService.save(any(Person.class))).thenAnswer(new PersonUpdatedAnswer());

        task.runTask();

        if (testName.startsWith("valid")) {
            verify(personService, times(8)).save(any(Person.class));
        } else {
            verify(personService, never()).save(any(Person.class));
        }
    }

    private static class PersonCreatedAnswer implements Answer<Person> {

        final AtomicInteger idGenerator = new AtomicInteger(0);

        @Override
        public Person answer(InvocationOnMock invocation) throws Throwable {
            final Person savedPerson = (Person)invocation.getArguments()[0];
            savedPerson.setId(idGenerator.incrementAndGet());
            return savedPerson;
        }
    }

    private static class PersonUpdatedAnswer implements Answer<Person> {

        @Override
        public Person answer(InvocationOnMock invocation) throws Throwable {
            return (Person)invocation.getArguments()[0];
        }
    }

    private static class ValidUtf8Config extends CheckinConfig {

        private ValidUtf8Config() {
            try {
                setXmlFile(new ClassPathResource("data-valid-utf8.xml").getFile().getCanonicalPath());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            setXmlFileEncoding("UTF-8");
        }
    }

    private static class InvalidXmlConfig extends CheckinConfig {

        private InvalidXmlConfig() {
            try {
                setXmlFile(new ClassPathResource("data-invalid-xml.xml").getFile().getCanonicalPath());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            setXmlFileEncoding("UTF-8");
        }
    }

    private static class FileNotFoundConfig extends CheckinConfig {

        private FileNotFoundConfig() {
            setXmlFile("data-inexistent.xml");
            setXmlFileEncoding("UTF-8");
        }
    }

    private static class ValidAnsiConfig extends CheckinConfig {

        private ValidAnsiConfig() {
            try {
                setXmlFile(new ClassPathResource("data-valid-ansi.xml").getFile().getCanonicalPath());
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            setXmlFileEncoding("ISO-8859-1");
        }
    }
}
