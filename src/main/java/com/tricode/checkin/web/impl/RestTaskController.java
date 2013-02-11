package com.tricode.checkin.web.impl;

import com.tricode.checkin.task.RunnableTask;
import com.tricode.checkin.task.RunnableTaskMetadata;
import com.tricode.checkin.web.RestTaskInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collection;

@Controller
public class RestTaskController implements RestTaskInterface {

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
    public void run(@RequestBody RunnableTaskMetadata taskMetadata) {
        for (RunnableTask task : tasks) {
            if (task.getClass().equals(taskMetadata.getTaskClass())) {
                task.runTask();
            }
        }
    }
}
