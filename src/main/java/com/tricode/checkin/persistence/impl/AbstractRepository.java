package com.tricode.checkin.persistence.impl;

import com.tricode.checkin.persistence.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class AbstractRepository<T> implements Repository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected QueryBuilderFactory queryBuilderFactory;

    public T get(int id) {
        return entityManager.find(getDaoClass(), id);
    }

    public T save(T entity) {
        return entityManager.merge(entity);
    }

    public List<T> findAll() {
        return entityManager.createQuery(queryBuilderFactory.newBuilder(getDaoClass()).toQuery()).getResultList();
    }

    protected abstract Class<T> getDaoClass();
}
