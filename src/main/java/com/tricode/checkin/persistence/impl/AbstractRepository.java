package com.tricode.checkin.persistence.impl;

import com.tricode.checkin.persistence.Repository;
import org.joda.time.YearMonth;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class AbstractRepository<T> implements Repository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected QueryBuilderFactory queryBuilderFactory;

    public <Y extends T> Y save(Y entity) {
        return entityManager.merge(entity);
    }
}
