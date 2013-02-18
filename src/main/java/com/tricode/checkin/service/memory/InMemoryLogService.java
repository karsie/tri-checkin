package com.tricode.checkin.service.memory;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.Log;
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

    private final Map<String, Log> data = new HashMap<String, Log>();
    private EventManager eventManager;

    @Autowired
    public InMemoryLogService(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void addLog(Log log) {
        data.put(toId(log), log);
        eventManager.raiseCreateEvent(log);
    }

    @Override
    public Collection<StatusChangeLog> listStatusChangesForUser(int userId) {
        final Collection<StatusChangeLog> result = new ArrayList<StatusChangeLog>();
        for (Log log : data.values()) {
            if (log instanceof StatusChangeLog) {
                final StatusChangeLog scl = (StatusChangeLog) log;
                if (scl.getUserId() == userId) {
                    result.add(scl);
                }
            }
        }
        return result;
    }

    @Override
    public Collection<StatusChangeLog> listStatusChangesForUser(int userId, long timestampFrom, long timestampTo) {
        final Collection<StatusChangeLog> result = new ArrayList<StatusChangeLog>();
        for (Log log : data.values()) {
            if (log instanceof StatusChangeLog) {
                final StatusChangeLog scl = (StatusChangeLog) log;
                if (scl.getUserId() == userId && scl.getTimestamp() >= timestampFrom && scl.getTimestamp() <= timestampTo) {
                    result.add(scl);
                }
            }
        }
        return result;
    }

    @Override
    public StatusChangeLog getPreviousStatusChangeForUser(int userId, LocationStatus status, long timestamp) {
        long latest = 0;
        StatusChangeLog result = null;
        for (Log log : data.values()) {
            if (log instanceof StatusChangeLog) {
                final StatusChangeLog scl = (StatusChangeLog) log;
                if (scl.getUserId() == userId && scl.getStatusTo() == status && scl.getTimestamp() > latest) {
                    if (latest > scl.getTimestamp()) {
                        break;
                    }
                    latest = scl.getTimestamp();
                    result = scl;
                }
            }
        }

        return result;
    }

    @Override
    public Collection<Log> list() {
        return data.values();
    }

    private static String toId(int userId, long timestamp) {
        return userId + "-" + timestamp;
    }

    private static String toId(Log log) {
        return toId(log.getUserId(), log.getTimestamp());
    }
}
