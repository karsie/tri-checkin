package com.tricode.checkin.event;

public interface EventListener<T> {

    void onEvent(T eventObject);

    Class<T> eventClass();

    EventType eventType();

}
