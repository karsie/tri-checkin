package com.tricode.checkin.web.impl;

import com.tricode.checkin.model.Person;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.service.PersonService;
import com.tricode.checkin.service.ReportingService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("rest/report")
public class RestReportController {

    private final PersonService personService;
    private final ReportingService reportingService;

    @Autowired
    public RestReportController(PersonService personService, ReportingService reportingService) {
        this.personService = personService;
        this.reportingService = reportingService;
    }

    @RequestMapping(value = "list/week", method = RequestMethod.GET)
    public
    @ResponseBody
    Collection<WeekReport> listWeekReport(@RequestParam Integer year, @RequestParam Integer week) {
        final Collection<Person> persons = personService.list();

        final Collection<WeekReport> results = new ArrayList<WeekReport>(persons.size());

        for (Person person : persons) {
            results.add(reportingService.get(person.getId(), year, week));
        }

        return results;
    }

    @RequestMapping(value = "list/years", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Integer> listYears() {
        return reportingService.listStoredYears();
    }

    @RequestMapping(value = "list/weeks/{year}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Integer> listWeeks(@PathVariable Integer year) {
        return reportingService.listStoredWeeks(year);
    }
}
