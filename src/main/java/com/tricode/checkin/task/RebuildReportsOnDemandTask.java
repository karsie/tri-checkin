package com.tricode.checkin.task;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.Log;
import com.tricode.checkin.service.LogService;
import com.tricode.checkin.service.ReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RebuildReportsOnDemandTask implements RunnableTask {

    private static final RunnableTaskMetadata METADATA = new RunnableTaskMetadata(RebuildReportsOnDemandTask.class, "Rebuilds all reports from the available logs", "On demand");

    private static final Logger log = LoggerFactory.getLogger(RebuildReportsOnDemandTask.class);

    private final LogService logService;

    private final ReportingService reportingService;

    private final EventManager eventManager;

    @Autowired
    public RebuildReportsOnDemandTask(LogService logService, ReportingService reportingService, EventManager eventManager) {
        this.logService = logService;
        this.reportingService = reportingService;
        this.eventManager = eventManager;
    }

    @Override
    public void runTask() {
        log.info("Starting task RebuildReportsOnDemandTask");
        StopWatchHelper stopWatch = StopWatchHelper.start();

        reportingService.clear();
        log.debug("Cleared all reports");
        for (Log logItem : logService.list()) {
            log.debug("Raising create on {}", logItem);
            eventManager.raiseCreateEvent(logItem);
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
            }
        }

        log.info("Task completed in {} milliseconds", stopWatch.stopAndGetMillis());
    }

    @Override
    public RunnableTaskMetadata metadata() {
        return METADATA;
    }
}
