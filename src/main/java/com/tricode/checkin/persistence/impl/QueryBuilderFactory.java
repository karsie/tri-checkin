package com.tricode.checkin.persistence.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

@Component
public class QueryBuilderFactory {

    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    public QueryBuilderFactory(final EntityManagerFactory entityManagerFactory) {
        criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
    }

    public <T> QueryBuilder<T> newBuilder(Class<T> queryClass) {
        CriteriaQuery<T> query = criteriaBuilder.createQuery(queryClass);
        Root<T> from = query.from(queryClass);
        query.select(from);

        return new QueryBuilder<T>(query, from);
    }

    public class QueryBuilder<T> {
        private final CriteriaQuery<T> criteriaQuery;
        private final Root<T> root;

        public QueryBuilder(CriteriaQuery<T> criteriaQuery, Root<T> root) {
            this.criteriaQuery = criteriaQuery;
            this.root = root;
        }

        public <Y> QueryBuilder<T> equal(SingularAttribute<? super T, Y> attribute, Y value) {
            criteriaQuery.where(criteriaBuilder.equal(root.get(attribute), value));
            return this;
        }

        public CriteriaQuery<T> toQuery() {
            return criteriaQuery;
        }
    }
}
