package com.tricode.checkin.persistence.impl;

import com.tricode.checkin.model.MonthReport;
import com.tricode.checkin.model.MonthReport_;
import com.tricode.checkin.model.UserReport;
import com.tricode.checkin.model.UserReport_;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.model.WeekReport_;
import com.tricode.checkin.persistence.AbstractRepository;
import com.tricode.checkin.persistence.UserReportRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;
import java.util.List;

@Repository
@Transactional
public class UserReportRepositoryImpl extends AbstractRepository<UserReport> implements UserReportRepository {

    @Override
    public <T extends UserReport> T get(Class<T> userReportClass, int id) {
        return entityManager.find(userReportClass, id);
    }

    @Override
    public WeekReport getWeekReportByYearAndWeek(int userId, int year, int week) {
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
    public List<Integer> findWeekReportYears() {
        final QueryBuilderFactory.AdvancedQueryBuilder<Integer, WeekReport> builder = queryBuilderFactory.newBuilder(Integer.class, WeekReport.class);
        builder.distinct(WeekReport_.year);

        return entityManager.createQuery(builder.toQuery()).getResultList();
    }

    @Override
    public List<Integer> findWeekReportWeeks(int year) {
        final QueryBuilderFactory.AdvancedQueryBuilder<Integer, WeekReport> builder = queryBuilderFactory.newBuilder(Integer.class, WeekReport.class);
        builder.where(WeekReport_.year, year);
        builder.distinct(WeekReport_.week);

        return entityManager.createQuery(builder.toQuery()).getResultList();
    }

    @Override
    public MonthReport getMonthReportByYearAndMonth(int userId, int year, int month) {
        final QueryBuilderFactory.SimpleQueryBuilder<MonthReport> builder = queryBuilderFactory.newBuilder(MonthReport.class);

        builder.and(builder.equal(MonthReport_.userId, userId),
                builder.equal(MonthReport_.year, year),
                builder.equal(MonthReport_.month, month));

        try {
            return entityManager.createQuery(builder.toQuery()).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Integer> findMonthReportYears() {
        final QueryBuilderFactory.AdvancedQueryBuilder<Integer, MonthReport> builder = queryBuilderFactory.newBuilder(Integer.class, MonthReport.class);
        builder.distinct(MonthReport_.year);

        return entityManager.createQuery(builder.toQuery()).getResultList();
    }

    @Override
    public List<Integer> findMonthReportMonths(int year) {
        final QueryBuilderFactory.AdvancedQueryBuilder<Integer, MonthReport> builder = queryBuilderFactory.newBuilder(Integer.class, MonthReport.class);
        builder.where(MonthReport_.year, year);
        builder.distinct(MonthReport_.month);

        return entityManager.createQuery(builder.toQuery()).getResultList();
    }

    @Override
    public <T extends UserReport> void clearAll(Class<T> userReportClass) {
        final CriteriaQuery<T> query = queryBuilderFactory.newBuilder(userReportClass).toQuery();

        for (T userReport : entityManager.createQuery(query).getResultList()) {
            entityManager.remove(userReport);
        }
    }
}
