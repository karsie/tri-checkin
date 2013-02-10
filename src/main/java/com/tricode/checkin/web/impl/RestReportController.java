package com.tricode.checkin.web.impl;

import com.tricode.checkin.model.Person;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.service.PersonService;
import com.tricode.checkin.service.ReportingService;
import com.tricode.checkin.web.RestReportInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class RestReportController implements RestReportInterface {

    private final PersonService personService;
    private final ReportingService reportingService;

    @Autowired
    public RestReportController(PersonService personService, ReportingService reportingService) {
        this.personService = personService;
        this.reportingService = reportingService;
    }

    @Override
    public Collection<WeekReport> listWeekReport(@RequestParam Integer year, @RequestParam Integer week) {
        final Collection<Person> persons = personService.list();

        final Collection<WeekReport> results = new ArrayList<WeekReport>(persons.size());

        for (Person person : persons) {
            results.add(reportingService.getWeek(person.getId(), year, week));
        }

        return results;
    }

    @Override
    public List<Integer> listYears() {
        return reportingService.listWeekReportYears();
    }

    @Override
    public List<Integer> listWeeks(@PathVariable Integer year) {
        return reportingService.listWeekReportWeeks(year);
    }
}
