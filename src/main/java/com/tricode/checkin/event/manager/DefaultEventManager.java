package com.tricode.checkin.event.manager;

import com.tricode.checkin.event.EventType;
import com.tricode.checkin.event.listener.EventListener;
import org.apache.commons.lang.ArrayUtils;
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
        doRaiseEvent(new EventListenerRunner<T>(null, eventObject, EventType.CREATE));
    }

    @Override
    public <T> void raiseUpdateEvent(T objectBefore, T objectAfter) {
        doRaiseEvent(new EventListenerRunner<T>(objectBefore, objectAfter, EventType.UPDATE));
    }

    @Override
    public <T> void raiseDeleteEvent(T eventObject) {
        doRaiseEvent(new EventListenerRunner<T>(eventObject, null, EventType.DELETE));
    }

    private <T> void doRaiseEvent(EventListenerRunner<T> runner) {
        if (eventListeners != null) {
            executor.execute(new EventListenersStarter<T>(eventListeners, executor, runner));
        }
    }

    private static class EventListenersStarter<T> implements Runnable {

        private final Collection<EventListener> listeners;
        private final ExecutorService executorService;
        private final EventListenerRunner<T> runner;

        private EventListenersStarter(final Collection<EventListener> listeners, final ExecutorService executorService,
                                      final EventListenerRunner<T> runner) {
            this.listeners = listeners;
            this.executorService = executorService;
            this.runner = runner;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void run() {
            final EventType[] types = {EventType.ALL, runner.runnerType()};

            for (EventListener eventListener : listeners) {
                if (eventListener.listenerClass().equals(runner.runnerClass())) {
                    if (ArrayUtils.contains(types, eventListener.listenerType())) {
                        executorService.execute(runner.getEventRunner(eventListener));
                    }
                }
            }
        }
    }

    private static class EventListenerRunner<T> {

        private final T objectBefore;
        private final T objectAfter;
        private final EventType eventType;
        private final Class<?> objectClass;

        private EventListenerRunner(T objectBefore, T objectAfter, EventType eventType) {
            this.objectBefore = objectBefore;
            this.objectAfter = objectAfter;
            this.eventType = eventType;

            if (objectBefore != null) {
                objectClass = objectBefore.getClass();
            } else {
                objectClass = objectAfter.getClass();
            }
        }

        private Runnable getEventRunner(final EventListener<T> eventListener) {
            return new Runnable() {
                @Override
                public void run() {
                    eventListener.onEvent(objectBefore, objectAfter, eventType);
                }
            };
        }

        private Class<?> runnerClass() {
            return objectClass;
        }

        private EventType runnerType() {
            return eventType;
        }
    }
}
