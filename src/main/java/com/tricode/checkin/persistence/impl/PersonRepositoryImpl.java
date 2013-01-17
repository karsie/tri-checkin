package com.tricode.checkin.persistence.impl;

import com.tricode.checkin.model.Person;
import com.tricode.checkin.model.Person_;
import com.tricode.checkin.persistence.PersonRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;

@Repository
@Transactional
public class PersonRepositoryImpl extends AbstractRepository<Person> implements PersonRepository {

    @Override
    protected Class<Person> getDaoClass() {
        return Person.class;
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
}
