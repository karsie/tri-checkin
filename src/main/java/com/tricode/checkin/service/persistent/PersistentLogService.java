package com.tricode.checkin.service.persistent;

import com.tricode.checkin.event.manager.EventManager;
import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.StatusChangeLog;
import com.tricode.checkin.persistence.StatusChangeLogRepository;
import com.tricode.checkin.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class PersistentLogService implements LogService {

    private final StatusChangeLogRepository statusChangeLogRepository;

    private final EventManager eventManager;

    @Autowired
    public PersistentLogService(StatusChangeLogRepository statusChangeLogRepository, EventManager eventManager) {
        this.statusChangeLogRepository = statusChangeLogRepository;
        this.eventManager = eventManager;
    }

    @Override
    public void addStatusChange(StatusChangeLog log) {
        statusChangeLogRepository.save(log);
        eventManager.raiseCreateEvent(log);
    }

    @Override
    public Collection<StatusChangeLog> listStatusChangesForUser(int userId) {
        return statusChangeLogRepository.findByUserId(userId);
    }

    @Override
    public Collection<StatusChangeLog> listStatusChangesForUser(int userId, long timestampFrom, long timestampTo) {
        return statusChangeLogRepository.findByUserIdAndTimeRange(userId, timestampFrom, timestampTo);
    }

    @Override
    public StatusChangeLog getLastStatusChangeForUser(int userId, LocationStatus status) {
        return statusChangeLogRepository.getLastByUserIdAndStatus(userId, status);
    }
}
