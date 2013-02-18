package com.tricode.checkin.web;

import com.tricode.checkin.model.MonthReport;
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

    @RequestMapping(value = "list/week/years", method = RequestMethod.GET)
    @ResponseBody
    Collection<Integer> weekReportYears();

    @RequestMapping(value = "list/week/weeks/{year}", method = RequestMethod.GET)
    @ResponseBody
    Collection<Integer> weekReportWeeks(@PathVariable Integer year);

    @RequestMapping(value = "list/month", method = RequestMethod.GET)
    @ResponseBody
    Collection<MonthReport> listMonthReport(@RequestParam Integer year, @RequestParam Integer month);

    @RequestMapping(value = "list/month/years", method = RequestMethod.GET)
    @ResponseBody
    Collection<Integer> monthReportYears();

    @RequestMapping(value = "list/month/months/{year}", method = RequestMethod.GET)
    @ResponseBody
    Collection<Integer> monthReportMonths(@PathVariable Integer year);
}
