package com.tricode.checkin.service.impl;

import org.springframework.stereotype.Component;

import static org.springframework.util.Assert.notNull;

@Component
public class ReportingIdFactory {

    public String get(int userId, int year, int week) {
        return userId + "-" + year + "-" + week;
    }
}
