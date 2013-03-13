package com.tricode.checkin.task;

import java.io.Serializable;

public class RunnableTaskMetadata implements Serializable {

    private static final long serialVersionUID = -854343822085941867L;

    private String taskClass;

    private String description;

    private String schedule;

    public RunnableTaskMetadata() {
    }

    public RunnableTaskMetadata(Class<? extends RunnableTask> taskClass, String description, String schedule) {
        this.taskClass = taskClass.getSimpleName();
        this.description = description;
        this.schedule = schedule;
    }

    public String getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RunnableTaskMetadata that = (RunnableTaskMetadata) o;

        if (!taskClass.equals(that.taskClass)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return taskClass.hashCode();
    }
}
