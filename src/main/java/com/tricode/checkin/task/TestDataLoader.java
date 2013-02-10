package com.tricode.checkin.task;

import com.tricode.checkin.service.ReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * This class is used to load dummy test data to simulate a live data set. Must not be used in production!.
 */
//@Component
public class TestDataLoader {

    private static final Logger log = LoggerFactory.getLogger(TestDataLoader.class);

    private ReportingService reportingService;

    @Autowired
    public TestDataLoader(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @PostConstruct
    private void loadData() {
        log.warn("*************  TestDataLoader ACTIVE!  **************");
    }
}
