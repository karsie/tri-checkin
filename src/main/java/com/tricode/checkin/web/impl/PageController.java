package com.tricode.checkin.web.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("index")
    public String index() {
        return "panel";
    }

    @RequestMapping("report/week")
    public String reportPerWeek() {
        return "report_week";
    }

    @RequestMapping("report/month")
    public String reportPerMonth() {
        return "report_month";
    }

    @RequestMapping("tasks")
    public void tasks() {
    }
}
