package com.tricode.checkin.event.listener;

import com.tricode.checkin.event.EventType;

public interface EventListener<T> {

    void onEvent(T objectBefore, T objectAfter, EventType eventType);

    Class<T> listenerClass();

    EventType listenerType();

}
