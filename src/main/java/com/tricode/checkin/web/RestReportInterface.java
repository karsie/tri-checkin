package com.tricode.checkin.web;

import com.tricode.checkin.model.WeekReport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@RequestMapping("rest/report")
public interface RestReportInterface {

    @RequestMapping(value = "list/week", method = RequestMethod.GET)
    @ResponseBody
    Collection<WeekReport> listWeekReport(@RequestParam Integer year, @RequestParam Integer week);

    @RequestMapping(value = "list/years", method = RequestMethod.GET)
    @ResponseBody
    Collection<Integer> listYears();

    @RequestMapping(value = "list/weeks/{year}", method = RequestMethod.GET)
    @ResponseBody
    Collection<Integer> listWeeks(@PathVariable Integer year);
}
