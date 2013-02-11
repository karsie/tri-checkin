package com.tricode.checkin.event.manager;

public interface EventManager {

    <T> void raiseCreateEvent(T eventObject);

    <T> void raiseUpdateEvent(T objectBefore, T objectAfter);

    <T> void raiseDeleteEvent(T eventObject);

    void stopForCurrentThread();

    void startForCurrentThread();
}
