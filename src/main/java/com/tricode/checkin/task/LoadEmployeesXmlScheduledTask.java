package com.tricode.checkin.task;

import com.tricode.checkin.config.CheckinConfig;
import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.Person;
import com.tricode.checkin.service.PersonService;
import com.tricode.checkin.xml.XmlEmployee;
import com.tricode.checkin.xml.XmlEmployees;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoadEmployeesXmlScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(LoadEmployeesXmlScheduledTask.class);

    private final CheckinConfig checkinConfig;
    private final PersonService personService;
    private Unmarshaller unmarshaller;

    @Autowired
    public LoadEmployeesXmlScheduledTask(CheckinConfig checkinConfig, PersonService personService) {
        this.checkinConfig = checkinConfig;
        this.personService = personService;

        try {
            JAXBContext context = JAXBContext.newInstance(XmlEmployees.class);
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            log.error("error while initializing jaxb", e);
            unmarshaller = null;
        }
    }

    @Scheduled(cron = "0 0 3 * * ?")
    @PostConstruct
    public void runTask() {
        if (unmarshaller != null) {
            try {
                importXmlEmployeeFiles();
            } catch (JAXBException e) {
                log.error("error parsing xml file", e);
                reSchedule();
            }
        }
    }

    private void importXmlEmployeeFiles() throws JAXBException {
        final String[] xmlFilePaths = StringUtils.split(checkinConfig.getXmlFile(), ',');
        for (String xmlFilePath : xmlFilePaths) {
            if (importXmlFile(xmlFilePath, XmlEmployees.class)) break;
        }
    }

    private <T> boolean importXmlFile(String xmlFilePath, Class<T> xmlClass) throws JAXBException {
        return importXmlFile(new File(xmlFilePath), xmlClass);
    }

    @SuppressWarnings("unchecked")
    private <T> boolean importXmlFile(File xmlFile, Class<T> xmlClass) throws JAXBException {
        if (xmlFile.exists()) {
            try {
                final String xmlData = IOUtils.toString(xmlFile.toURI(), checkinConfig.getXmlFileEncoding());

                final T xmlObjects = (T) unmarshaller.unmarshal(new StringReader(xmlData));
                importXmlObjects(xmlObjects, xmlClass);

                return true;
            } catch (IOException e) {
                log.error("error reading xml file", e);
            }
        }
        return false;
    }

    private <T> void importXmlObjects(T xmlObjects, Class<T> xmlClass) {
        if (xmlClass.equals(XmlEmployees.class)) {
            importXmlEmployees((XmlEmployees) xmlObjects);
        }
    }

    private void importXmlEmployees(XmlEmployees employees) {
        int newPersons = 0;
        int updatedPersons = 0;
        for (XmlEmployee employee : employees.getEmployees()) {
            Person person = personService.getByExternalId(employee.getId());
            if (person == null) {
                person = Person.Builder.empty().withExternalId(employee.getId()).withStatus(LocationStatus.OUT).get();
                newPersons++;
            } else {
                updatedPersons++;
            }
            person.setName(employee.getName());
            if (employee.getBirthDate() != null) {
                person.setBirthDate(employee.getBirthDate());
            }

            if (employee.getStartDate() != null) {
                person.setStartDate(employee.getStartDate());
            }

            final String[] name = StringUtils.split(employee.getName(), ' ');
            if (name.length > 0) {
                person.setFirst(name[0]);

                if (name.length > 1) {
                    person.setLast(parseLastName(name));
                }
            }
            final Person saved = personService.save(person);
            log.debug("imported person [{}] - [{}]", saved.getId(), employee.getName());
        }
        log.info("imported {} new person(s), {} updated", newPersons, updatedPersons);
    }

    private static String parseLastName(String[] nameParts) {
        String lastName = nameParts[nameParts.length - 1];

        if (nameParts.length > 2) {
            final List<String> prefix = new ArrayList<String>();
            // don't add first name
            // don't add last name
            // don't add name parts that are not all lowercase (second names)
            for (int i = 1; i < nameParts.length - 1; i++) {
                if (StringUtils.isAllLowerCase(nameParts[i])) {
                    prefix.add(nameParts[i]);
                }
            }
            if (!prefix.isEmpty()) {
                lastName += ", " + StringUtils.join(prefix, ' ');
            }
        }
        return lastName;
    }


    private void reSchedule() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runTask();
            }
        });
    }
}
