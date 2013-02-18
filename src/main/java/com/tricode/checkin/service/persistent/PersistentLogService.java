package com.tricode.checkin.service.persistent;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.EatingInLog;
import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.Log;
import com.tricode.checkin.model.StatusChangeLog;
import com.tricode.checkin.persistence.LogRepository;
import com.tricode.checkin.service.AbstractService;
import com.tricode.checkin.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class PersistentLogService extends AbstractService implements LogService {

    private final LogRepository logRepository;

    @Autowired
    public PersistentLogService(LogRepository logRepository, EventManager eventManager) {
        super(eventManager);
        this.logRepository = logRepository;
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
    public StatusChangeLog getPreviousStatusChangeForUser(int userId, LocationStatus status, long timestamp) {
        return logRepository.getPreviousChangeByUserIdAndStatusFromTimestamp(userId, status, timestamp);
    }

    @Override
    public Collection<Log> list() {
        final Collection<Log> result = new ArrayList<Log>();

        result.addAll(logRepository.findAll(StatusChangeLog.class));
        result.addAll(logRepository.findAll(EatingInLog.class));

        return result;
    }
}
