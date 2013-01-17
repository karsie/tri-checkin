package com.tricode.checkin.persistence.impl;

import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.model.WeekReport_;
import com.tricode.checkin.persistence.WeekReportRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Repository
@Transactional
public class WeekReportRepositoryImpl extends AbstractRepository<WeekReport> implements WeekReportRepository {

    @Override
    protected Class<WeekReport> getDaoClass() {
        return WeekReport.class;
    }

    @Override
    public WeekReport getByUserIdYearAndWeek(int userId, int year, int week) {
        final QueryBuilderFactory.QueryBuilder<WeekReport> builder = queryBuilderFactory
                                .newBuilder(WeekReport.class);

        builder.and(builder.equal(WeekReport_.userId, userId),
                builder.equal(WeekReport_.year, year),
                builder.equal(WeekReport_.week, week));

        try {
            return entityManager.createQuery(builder.toQuery()).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
