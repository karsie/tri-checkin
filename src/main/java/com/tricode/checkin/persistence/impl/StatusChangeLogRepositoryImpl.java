package com.tricode.checkin.persistence.impl;

import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.StatusChangeLog;
import com.tricode.checkin.model.StatusChangeLog_;
import com.tricode.checkin.persistence.StatusChangeLogRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;

@Repository
@Transactional
public class StatusChangeLogRepositoryImpl extends AbstractRepository<StatusChangeLog> implements
                                                                                       StatusChangeLogRepository {

    @Override
    protected Class<StatusChangeLog> getDaoClass() {
        return StatusChangeLog.class;
    }

    @Override
    public Collection<StatusChangeLog> findByUserId(int userId) {
        final CriteriaQuery<StatusChangeLog> query = queryBuilderFactory.newBuilder(
                StatusChangeLog.class).where(StatusChangeLog_.userId, userId).toQuery();

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Collection<StatusChangeLog> findByUserIdAndTimeRange(int userId, long timestampFrom, long timestampTo) {
        final QueryBuilderFactory.QueryBuilder<StatusChangeLog> builder = queryBuilderFactory
                        .newBuilder(StatusChangeLog.class);

        builder.and(builder.equal(StatusChangeLog_.userId, userId),
                    builder.between(StatusChangeLog_.timestamp, timestampFrom, timestampTo))
                .orderBy(StatusChangeLog_.timestamp, true);

        return entityManager.createQuery(builder.toQuery()).getResultList();
    }

    @Override
    public StatusChangeLog getLastByUserIdAndStatus(int userId, LocationStatus status) {
        final QueryBuilderFactory.QueryBuilder<StatusChangeLog> builder = queryBuilderFactory
                .newBuilder(StatusChangeLog.class);

        builder.and(builder.equal(StatusChangeLog_.userId, userId),
                    builder.equal(StatusChangeLog_.statusTo, status))
                .orderBy(StatusChangeLog_.timestamp, false);
        try {
            return entityManager.createQuery(builder.toQuery()).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
