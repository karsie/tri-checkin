package com.tricode.checkin.service.persistent;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.Log;
import com.tricode.checkin.model.StatusChangeLog;
import com.tricode.checkin.persistence.LogRepository;
import com.tricode.checkin.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class PersistentLogService implements LogService {

    private final LogRepository logRepository;

    private final EventManager eventManager;

    @Autowired
    public PersistentLogService(LogRepository logRepository, EventManager eventManager) {
        this.logRepository = logRepository;
        this.eventManager = eventManager;
    }

    @Override
    public void addLog(Log log) {
        logRepository.save(log);
        eventManager.raiseCreateEvent(log);
    }

    @Override
    public Collection<StatusChangeLog> listStatusChangesForUser(int userId) {
        return logRepository.findByUserId(StatusChangeLog.class, userId);
    }

    @Override
    public Collection<StatusChangeLog> listStatusChangesForUser(int userId, long timestampFrom, long timestampTo) {
        return logRepository.findByUserIdAndTimeRange(StatusChangeLog.class, userId, timestampFrom, timestampTo);
    }

    @Override
    public StatusChangeLog getLastStatusChangeForUser(int userId, LocationStatus status) {
        return logRepository.getLastChangeByUserIdAndStatus(userId, status);
    }
}
