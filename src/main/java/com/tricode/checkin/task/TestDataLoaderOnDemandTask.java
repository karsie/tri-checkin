package com.tricode.checkin.task;

import com.tricode.checkin.service.ReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class is used to load dummy test data to simulate a live data set. Must not be used in production!.
 */
@Component
public class TestDataLoaderOnDemandTask implements RunnableTask {

    private static final Logger log = LoggerFactory.getLogger(TestDataLoaderOnDemandTask.class);

    private static final RunnableTaskMetadata METADATA = new RunnableTaskMetadata(TestDataLoaderOnDemandTask.class, "Loads test data", "On demand");

    private ReportingService reportingService;

    @Autowired
    public TestDataLoaderOnDemandTask(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @Override
    public void runTask() {
        log.warn("*************  TestDataLoader ACTIVATED!  **************");
    }

    @Override
    public RunnableTaskMetadata metadata() {
        return METADATA;
    }
}
