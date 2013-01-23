package com.tricode.checkin.persistence;

public interface Repository<T> {

    <Y extends T> Y save(Y entity);
}
