package com.tricode.checkin.web.impl;

import com.tricode.checkin.task.RunnableTask;
import com.tricode.checkin.task.RunnableTaskMetadata;
import com.tricode.checkin.web.RestTaskInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public class RestTaskController implements RestTaskInterface {

    private static final Logger log = LoggerFactory.getLogger(RestTaskController.class);

    private final Collection<RunnableTask> tasks;

    @Autowired
    public RestTaskController(Collection<RunnableTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public Collection<RunnableTaskMetadata> list() {
        final Collection<RunnableTaskMetadata> list = new ArrayList<RunnableTaskMetadata>(tasks.size());
        for (RunnableTask task : tasks) {
            // TODO add running state to metadata
            // TODO add last run time to metadata
            list.add(task.metadata());
        }
        return list;
    }

    @Override
    public boolean run(@RequestBody RunnableTaskMetadata taskMetadata) {
        for (RunnableTask task : tasks) {
            if (task.getClass().getSimpleName().equals(taskMetadata.getTaskClass())) {
                task.runTask();
                return true;
            }
        }
        return false;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void exception(Exception e) {
        log.error("error in controller", e);
    }
}
