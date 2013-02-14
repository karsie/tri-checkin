package com.tricode.checkin.task;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.Log;
import com.tricode.checkin.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RebuildReportsOnDemandTask implements RunnableTask {

    private static final RunnableTaskMetadata METADATA = new RunnableTaskMetadata(RebuildReportsOnDemandTask.class, "Rebuilds all reports from the available logs", "On demand");

    private final LogService logService;

    private final EventManager eventManager;

    @Autowired
    public RebuildReportsOnDemandTask(LogService logService, EventManager eventManager) {
        this.logService = logService;
        this.eventManager = eventManager;
    }

    @Override
    public void runTask() {
        for (Log log : logService.list()) {
            eventManager.raiseCreateEvent(log);
        }
    }

    @Override
    public RunnableTaskMetadata metadata() {
        return METADATA;
    }
}
