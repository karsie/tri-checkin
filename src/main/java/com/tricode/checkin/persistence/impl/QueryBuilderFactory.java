package com.tricode.checkin.persistence.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

@Component
public class QueryBuilderFactory {

    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    public QueryBuilderFactory(final EntityManagerFactory entityManagerFactory) {
        criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
    }

    public <F> SimpleQueryBuilder<F> newBuilder(Class<F> fromClass) {
        final CriteriaQuery<F> query = criteriaBuilder.createQuery(fromClass);
        final Root<F> root = query.from(fromClass);
        query.select(root);

        return new SimpleQueryBuilder<F>(query, root);
    }

    public <S, F> AdvancedQueryBuilder<S, F> newBuilder(Class<S> selectClass, Class<F> fromClass) {
        final CriteriaQuery<S> query = criteriaBuilder.createQuery(selectClass);
        final Root<F> root = query.from(fromClass);

        return new AdvancedQueryBuilder<S, F>(query, root);
    }

    public class SimpleQueryBuilder<F> extends AdvancedQueryBuilder<F, F> {
        public SimpleQueryBuilder(CriteriaQuery<F> criteriaQuery, Root<F> root) {
            super(criteriaQuery, root);
        }
    }

    public class AdvancedQueryBuilder<S, F> {
        private final CriteriaQuery<S> criteriaQuery;
        private final Root<F> root;

        public AdvancedQueryBuilder(CriteriaQuery<S> criteriaQuery, Root<F> root) {
            this.criteriaQuery = criteriaQuery;
            this.root = root;
        }

        public AdvancedQueryBuilder<S, F> distinct(SingularAttribute<? super F, S> attribute) {
            criteriaQuery.select(root.get(attribute)).distinct(true);
            return this;
        }

        public <Y> AdvancedQueryBuilder<S, F> where(SingularAttribute<? super F, Y> attribute, Y value) {
            criteriaQuery.where(criteriaBuilder.equal(root.get(attribute), value));
            return this;
        }

        public AdvancedQueryBuilder<S, F> and(Predicate... restrictions) {
            criteriaQuery.where(criteriaBuilder.and(restrictions));
            return this;
        }

        public <Y> AdvancedQueryBuilder<S, F> orderBy(SingularAttribute<? super F, Y> attribute, boolean asc) {
            if (asc) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(attribute)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(attribute)));
            }
            return this;
        }

        public <Y> Predicate equal(SingularAttribute<? super F, Y> attribute, Y value) {
            return criteriaBuilder.equal(root.get(attribute), value);
        }

        public <Y extends Comparable<? super Y>> Predicate between(SingularAttribute<? super F, Y> attribute, Y valueX, Y valueY) {
            return criteriaBuilder.between(root.get(attribute), valueX, valueY);
        }

        public CriteriaQuery<S> toQuery() {
            return criteriaQuery;
        }
    }
}
