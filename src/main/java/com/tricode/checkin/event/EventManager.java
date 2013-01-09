package com.tricode.checkin.event;

public interface EventManager {

    <T> void raiseCreateEvent(T eventObject);

    <T> void raiseUpdateEvent(T eventObject);

    <T> void raiseDeleteEvent(T eventObject);

}
