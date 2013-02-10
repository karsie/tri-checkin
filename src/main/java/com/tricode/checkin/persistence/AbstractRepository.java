package com.tricode.checkin.persistence;

import com.tricode.checkin.persistence.Repository;
import com.tricode.checkin.persistence.impl.QueryBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractRepository<T> implements Repository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected QueryBuilderFactory queryBuilderFactory;

    public <Y extends T> Y save(Y entity) {
        return entityManager.merge(entity);
    }
}
