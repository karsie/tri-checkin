package com.tricode.checkin.persistence.impl;

import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.model.WeekReport_;
import com.tricode.checkin.persistence.WeekReportRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
@Transactional
public class WeekReportRepositoryImpl extends AbstractRepository<WeekReport> implements WeekReportRepository {

    @Override
    protected Class<WeekReport> getDaoClass() {
        return WeekReport.class;
    }

    @Override
    public WeekReport getByUserIdYearAndWeek(int userId, int year, int week) {
        final QueryBuilderFactory.SimpleQueryBuilder<WeekReport> builder = queryBuilderFactory.newBuilder(WeekReport.class);

        builder.and(builder.equal(WeekReport_.userId, userId),
                builder.equal(WeekReport_.year, year),
                builder.equal(WeekReport_.week, week));

        try {
            return entityManager.createQuery(builder.toQuery()).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Integer> findYears() {
        final QueryBuilderFactory.AdvancedQueryBuilder<Integer, WeekReport> builder = queryBuilderFactory.newBuilder(Integer.class, WeekReport.class);
        builder.distinct(WeekReport_.year);

        return entityManager.createQuery(builder.toQuery()).getResultList();
    }

    @Override
    public List<Integer> findWeeks(int year) {
        final QueryBuilderFactory.AdvancedQueryBuilder<Integer, WeekReport> builder = queryBuilderFactory.newBuilder(Integer.class, WeekReport.class);
        builder.where(WeekReport_.year, year);
        builder.distinct(WeekReport_.week);

        return entityManager.createQuery(builder.toQuery()).getResultList();
    }
}
