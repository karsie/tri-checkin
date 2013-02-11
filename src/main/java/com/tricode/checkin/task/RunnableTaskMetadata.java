package com.tricode.checkin.task;

import java.io.Serializable;

public class RunnableTaskMetadata implements Serializable {

    private static final long serialVersionUID = -854343822085941867L;

    private final Class<? extends RunnableTask> taskClass;

    private final String description;

    private final String schedule;

    public RunnableTaskMetadata(Class<? extends RunnableTask> taskClass, String description, String schedule) {
        this.taskClass = taskClass;
        this.description = description;
        this.schedule = schedule;
    }

    public Class<? extends RunnableTask> getTaskClass() {
        return taskClass;
    }

    public String getDescription() {
        return description;
    }

    public String getSchedule() {
        return schedule;
    }
}
