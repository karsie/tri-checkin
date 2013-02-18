package com.tricode.checkin.service;

import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.Log;
import com.tricode.checkin.model.StatusChangeLog;

import java.util.Collection;

public interface LogService {

    void addLog(Log log);

    Collection<StatusChangeLog> listStatusChangesForUser(int userId);

    Collection<StatusChangeLog> listStatusChangesForUser(int userId, long timestampFrom, long timestampTo);

    StatusChangeLog getPreviousStatusChangeForUser(int userId, LocationStatus status, long timestamp);

    Collection<Log> list();
}
