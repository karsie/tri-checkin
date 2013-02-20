package com.tricode.checkin.task;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.Log;
import com.tricode.checkin.service.LogService;
import com.tricode.checkin.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RebuildReportsOnDemandTask implements RunnableTask {

    private static final RunnableTaskMetadata METADATA = new RunnableTaskMetadata(RebuildReportsOnDemandTask.class, "Rebuilds all reports from the available logs", "On demand");

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
        reportingService.clear();
        for (Log log : logService.list()) {
            eventManager.raiseCreateEvent(log);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public RunnableTaskMetadata metadata() {
        return METADATA;
    }
}
