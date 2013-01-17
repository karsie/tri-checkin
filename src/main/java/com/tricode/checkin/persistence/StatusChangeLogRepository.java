package com.tricode.checkin.persistence;

import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.StatusChangeLog;

import java.util.Collection;

public interface StatusChangeLogRepository extends Repository<StatusChangeLog> {

    Collection<StatusChangeLog> findByUserId(int userId);

    Collection<StatusChangeLog> findByUserIdAndTimeRange(int userId, long timestampFrom, long timestampTo);

    StatusChangeLog getLastByUserIdAndStatus(int userId, LocationStatus status);
}
