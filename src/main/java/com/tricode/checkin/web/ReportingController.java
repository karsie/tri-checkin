package com.tricode.checkin.web;

import com.tricode.checkin.model.Person;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.service.PersonService;
import com.tricode.checkin.service.ReportingService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("report")
public class ReportingController {

    private final PersonService personService;
    private final ReportingService reportingService;

    @Autowired
    public ReportingController(PersonService personService, ReportingService reportingService) {
        this.personService = personService;
        this.reportingService = reportingService;
    }

    @RequestMapping
    public String index() {
        return "report";
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public
    @ResponseBody
    Collection<WeekReport> getReportData() {
        final DateTime currentDate = new DateTime();
        final Collection<Person> persons = personService.list();

        final Collection<WeekReport> results = new ArrayList<WeekReport>(persons.size());

        for (Person person : persons) {
            results.add(reportingService.get(person.getId(), currentDate.getWeekyear(), currentDate.getWeekOfWeekyear()));
        }

        return results;
    }
}
