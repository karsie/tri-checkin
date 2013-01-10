package com.tricode.checkin.service.impl;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.StatusChangeLog;
import com.tricode.checkin.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryLogService implements LogService {

    private final Map<String, StatusChangeLog> data = new HashMap<String, StatusChangeLog>();
    private EventManager eventManager;

    @Autowired
    public InMemoryLogService(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void addStatusChange(StatusChangeLog log) {
        data.put(log.toId(), log);
        eventManager.raiseCreateEvent(log);
    }

    @Override
    public Collection<StatusChangeLog> listStatusChangesForUser(int userId) {
        final Collection<StatusChangeLog> result = new ArrayList<StatusChangeLog>();
        for (StatusChangeLog log : data.values()) {
            if (log.getUserId() == userId) {
                result.add(log);
            }
        }
        return result;
    }

    @Override
    public Collection<StatusChangeLog> listStatusChangesForUser(int userId, long timestampFrom, long timestampTo) {
        final Collection<StatusChangeLog> result = new ArrayList<StatusChangeLog>();
        for (StatusChangeLog log : data.values()) {
            if (log.getUserId() == userId && log.getTimestamp() >= timestampFrom && log.getTimestamp() <= timestampTo) {
                result.add(log);
            }
        }
        return result;
    }

    @Override
    public StatusChangeLog getLastStatusChangeForUser(int userId, LocationStatus status) {
        long latest = 0;
        StatusChangeLog result = null;
        for (StatusChangeLog log : data.values()) {
            if (log.getUserId() == userId && log.getStatusTo() == status && log.getTimestamp() > latest) {
                latest = log.getTimestamp();
                result = log;
            }
        }

        return result;
    }
}
