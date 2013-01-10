package com.tricode.checkin.service;

import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.StatusChangeLog;

import java.util.Collection;

public interface LogService {

    void addStatusChange(StatusChangeLog log);

    Collection<StatusChangeLog> listStatusChangesForUser(int userId);

    Collection<StatusChangeLog> listStatusChangesForUser(int userId, long timestampFrom, long timestampTo);

    StatusChangeLog getLastStatusChangeForUser(int userId, LocationStatus status);

}
