package com.tricode.checkin.persistence;

import java.util.List;

public interface Repository<T> {

    T get(int id);

    T save(T entity);

    List<T> findAll();

}
