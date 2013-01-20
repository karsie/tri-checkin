package com.tricode.checkin.persistence;

import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.Log;
import com.tricode.checkin.model.StatusChangeLog;

import java.util.Collection;

public interface LogRepository extends Repository<Log> {

    <T extends Log> Collection<T> findByUserId(Class<T> logClass, int userId);

    Collection<Log> findByUserId(int userId);

    <T extends Log> Collection<T> findByUserIdAndTimeRange(Class<T> logClass, int userId, long timestampFrom, long timestampTo);

    Collection<Log> findByUserIdAndTimeRange(int userId, long timestampFrom, long timestampTo);

    StatusChangeLog getLastChangeByUserIdAndStatus(int userId, LocationStatus status);
}
