package com.tricode.checkin.persistence.impl;

import com.tricode.checkin.model.*;
import com.tricode.checkin.persistence.LogRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;

@Repository
@Transactional
public class LogRepositoryImpl extends AbstractRepository<Log> implements LogRepository {

    @Override
    public <T extends Log> Collection<T> findByUserId(Class<T> logClass, int userId) {
        final CriteriaQuery<T> query = queryBuilderFactory.newBuilder(logClass).where(Log_.userId, userId).toQuery();

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Collection<Log> findByUserId(int userId) {
        return findByUserId(Log.class, userId);
    }

    @Override
    public <T extends Log> Collection<T> findByUserIdAndTimeRange(Class<T> logClass, int userId, long timestampFrom, long timestampTo) {
        final QueryBuilderFactory.SimpleQueryBuilder<T> builder = queryBuilderFactory.newBuilder(logClass);

        builder.and(builder.equal(Log_.userId, userId),
                builder.between(Log_.timestamp, timestampFrom, timestampTo))
                .orderBy(Log_.timestamp, true);

        return entityManager.createQuery(builder.toQuery()).getResultList();
    }

    @Override
    public Collection<Log> findByUserIdAndTimeRange(int userId, long timestampFrom, long timestampTo) {
        return findByUserIdAndTimeRange(Log.class, userId, timestampFrom, timestampTo);
    }

    @Override
    public StatusChangeLog getLastChangeByUserIdAndStatus(int userId, LocationStatus status) {
        final QueryBuilderFactory.SimpleQueryBuilder<StatusChangeLog> builder = queryBuilderFactory
                .newBuilder(StatusChangeLog.class);

        builder.and(builder.equal(StatusChangeLog_.userId, userId),
                builder.equal(StatusChangeLog_.statusTo, status))
                .orderBy(StatusChangeLog_.timestamp, false);
        try {
            return entityManager.createQuery(builder.toQuery()).setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}