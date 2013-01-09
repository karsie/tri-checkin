package com.tricode.checkin.event.impl;

import com.tricode.checkin.event.EventListener;
import com.tricode.checkin.event.EventManager;
import com.tricode.checkin.event.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class DefaultEventManager implements EventManager {

    @Autowired(required = false)
    private Collection<EventListener> eventListeners;

    private ExecutorService executor;

    @PostConstruct
    private void init() {
        executor = Executors.newCachedThreadPool();
    }

    @PreDestroy
    private void destroy() {
        executor.shutdown();
    }

    @Override
    public <T> void raiseCreateEvent(T eventObject) {
        doRaiseEvent(eventObject, EventType.CREATE);
    }

    @Override
    public <T> void raiseUpdateEvent(T eventObject) {
        doRaiseEvent(eventObject, EventType.UPDATE);
    }

    @Override
    public <T> void raiseDeleteEvent(T eventObject) {
        doRaiseEvent(eventObject, EventType.DELETE);
    }

    @SuppressWarnings("unchecked")
    private <T> void doRaiseEvent(final T eventObject, EventType eventType) {
        if (eventListeners != null) {
            for (EventListener eventListener : eventListeners) {
                if (eventListener.eventClass().equals(eventObject.getClass()) && (eventListener.eventType() == EventType.ALL || eventListener.eventType() == eventType)) {
                    executor.execute(new EventListenerRunner<T>(eventListener, eventObject));
                }
            }
        }
    }

    private static class EventListenerRunner<T> implements Runnable {

        private final EventListener<T> eventListener;

        private final T eventObject;

        private EventListenerRunner(EventListener<T> eventListener, T eventObject) {
            this.eventListener = eventListener;
            this.eventObject = eventObject;
        }

        @Override
        public void run() {
            eventListener.onEvent(eventObject);
        }
    }
}
