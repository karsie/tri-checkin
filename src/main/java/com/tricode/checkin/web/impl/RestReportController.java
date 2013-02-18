package com.tricode.checkin.web.impl;

import com.tricode.checkin.model.MonthReport;
import com.tricode.checkin.model.Person;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.service.PersonService;
import com.tricode.checkin.service.ReportingService;
import com.tricode.checkin.web.RestReportInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public class RestReportController implements RestReportInterface {

    private static final Logger log = LoggerFactory.getLogger(RestReportController.class);

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
    public Collection<Integer> weekReportYears() {
        return reportingService.listWeekReportYears();
    }

    @Override
    public Collection<Integer> weekReportWeeks(@PathVariable Integer year) {
        return reportingService.listWeekReportWeeks(year);
    }

    @Override
    public Collection<MonthReport> listMonthReport(@RequestParam Integer year, @RequestParam Integer month) {
        final Collection<Person> persons = personService.list();

        final Collection<MonthReport> results = new ArrayList<MonthReport>(persons.size());

        for (Person person : persons) {
            results.add(reportingService.getMonth(person.getId(), year, month));
        }

        return results;
    }

    @Override
    public Collection<Integer> monthReportYears() {
        return reportingService.listMonthReportYears();
    }

    @Override
    public Collection<Integer> monthReportMonths(@PathVariable Integer year) {
        return reportingService.listMonthReportMonths(year);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void exception(Exception e) {
        log.error("error in controller", e);
    }
}
