package com.tricode.checkin.persistence.impl;

import com.tricode.checkin.model.Person;
import com.tricode.checkin.model.Person_;
import com.tricode.checkin.persistence.AbstractRepository;
import com.tricode.checkin.persistence.PersonRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;

@Repository
@Transactional
public class PersonRepositoryImpl extends AbstractRepository<Person> implements PersonRepository {

    @Override
    public Person get(int id) {
        return entityManager.find(Person.class, id);
    }

    @Override
    public Person getByExternalId(final String externalId) {
        final CriteriaQuery<Person> query = queryBuilderFactory.newBuilder(Person.class).where(Person_.externalId,
                                                                                               externalId).toQuery();
        try {
            return entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Collection<Person> findAll() {
        return entityManager.createQuery(queryBuilderFactory.newBuilder(Person.class).toQuery()).getResultList();
    }
}
