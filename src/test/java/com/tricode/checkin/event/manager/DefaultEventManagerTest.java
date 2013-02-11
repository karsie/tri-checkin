package com.tricode.checkin.event.manager;

import com.tricode.checkin.event.EventType;
import com.tricode.checkin.event.listener.EventListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class DefaultEventManagerTest {

    private DefaultEventManager eventManager;

    private CountDownLatch latch;

    @Before
    public void setUp() {
        eventManager = new DefaultEventManager();
        latch = new CountDownLatch(1);
    }

    @After
    public void tearDown() {
        ReflectionTestUtils.invokeMethod(eventManager, "destroy");
    }

    @Test
    public void whenRaiseCreateEventThenAllEventListenerIsTriggered() throws Exception {
        registerListener(new CountDownAllListener());

        eventManager.raiseCreateEvent(new CountDown());

        assertThat(latch.await(1, TimeUnit.SECONDS), is(true));
    }

    @Test
    public void whenRaiseCreateEventThenCreateEventListenerIsTriggered() throws Exception {
        registerListener(new CountDownCreateListener());

        eventManager.raiseCreateEvent(new CountDown());

        assertThat(latch.await(1, TimeUnit.SECONDS), is(true));
    }

    @Test
    public void whenRaiseCreateEventThenUpdateEventListenerIsNotTriggered() throws Exception {
        registerListener(new CountDownUpdateListener());

        eventManager.raiseCreateEvent(new CountDown());

        assertThat(latch.await(1, TimeUnit.SECONDS), is(false));
    }

    @Test
    public void whenRaiseCreateEventThenDeleteEventListenerIsNotTriggered() throws Exception {
        registerListener(new CountDownDeleteListener());

        eventManager.raiseCreateEvent(new CountDown());

        assertThat(latch.await(1, TimeUnit.SECONDS), is(false));
    }

    @Test
    public void whenRaiseUpdateEventThenAllEventListenerIsTriggered() throws Exception {
        registerListener(new CountDownAllListener());

        eventManager.raiseUpdateEvent(new CountDown(), new CountDown());

        assertThat(latch.await(1, TimeUnit.SECONDS), is(true));
    }

    @Test
    public void whenRaiseUpdateEventThenUpdateEventListenerIsTriggered() throws Exception {
        registerListener(new CountDownUpdateListener());

        eventManager.raiseUpdateEvent(new CountDown(), new CountDown());

        assertThat(latch.await(1, TimeUnit.SECONDS), is(true));
    }

    @Test
    public void whenRaiseUpdateEventThenCreateEventListenerIsNotTriggered() throws Exception {
        registerListener(new CountDownCreateListener());

        eventManager.raiseUpdateEvent(new CountDown(), new CountDown());

        assertThat(latch.await(1, TimeUnit.SECONDS), is(false));
    }

    @Test
    public void whenRaiseUpdateEventThenDeleteEventListenerIsNotTriggered() throws Exception {
        registerListener(new CountDownDeleteListener());

        eventManager.raiseUpdateEvent(new CountDown(), new CountDown());

        assertThat(latch.await(1, TimeUnit.SECONDS), is(false));
    }

    @Test
    public void whenRaiseDeleteEventThenAllEventListenerIsTriggered() throws Exception {
        registerListener(new CountDownAllListener());

        eventManager.raiseDeleteEvent(new CountDown());

        assertThat(latch.await(1, TimeUnit.SECONDS), is(true));
    }

    @Test
    public void whenRaiseDeleteEventThenDeleteEventListenerIsTriggered() throws Exception {
        registerListener(new CountDownDeleteListener());

        eventManager.raiseDeleteEvent(new CountDown());

        assertThat(latch.await(1, TimeUnit.SECONDS), is(true));
    }

    @Test
    public void whenRaiseDeleteEventThenCreateEventListenerIsNotTriggered() throws Exception {
        registerListener(new CountDownCreateListener());

        eventManager.raiseDeleteEvent(new CountDown());

        assertThat(latch.await(1, TimeUnit.SECONDS), is(false));
    }

    @Test
    public void whenRaiseDeleteEventThenUpdateEventListenerIsNotTriggered() throws Exception {
        registerListener(new CountDownUpdateListener());

        eventManager.raiseDeleteEvent(new CountDown());

        assertThat(latch.await(1, TimeUnit.SECONDS), is(false));
    }

    @Test
    public void whenStoppedThenNoListenerIsTriggered() throws Exception {
        registerListener(new CountDownAllListener());

        eventManager.stopForCurrentThread();

        eventManager.raiseCreateEvent(new CountDown());

        assertThat(latch.await(1, TimeUnit.SECONDS), is(false));
    }

    @Test
    public void whenRestartedThenEventsTriggeredWhenStoppedAreIgnored() throws Exception {
        registerListener(new CountDownAllListener());

        latch = new CountDownLatch(2);

        eventManager.stopForCurrentThread();

        eventManager.raiseCreateEvent(new CountDown());

        eventManager.startForCurrentThread();

        eventManager.raiseDeleteEvent(new CountDown());

        // only triggered once, not twice
        assertThat(latch.await(1, TimeUnit.SECONDS), is(false));
        assertThat(latch.getCount(), is(equalTo(1L)));
    }

    @Test
    public void whenNoListenersThenRaiseEventIsIgnored() throws Exception {
        eventManager.raiseCreateEvent(new CountDown());

        assertThat(latch.await(1, TimeUnit.SECONDS), is(false));
    }

    private void registerListener(EventListener<CountDown> listener) {
        ReflectionTestUtils.setField(eventManager, "eventListeners", Arrays.<EventListener>asList(listener));
    }

    private static class CountDown {
    }

    private class CountDownAllListener implements EventListener<CountDown> {

        @Override
        public void onEvent(CountDown objectBefore, CountDown objectAfter, EventType eventType) {
            latch.countDown();
        }

        @Override
        public Class<CountDown> listenerClass() {
            return CountDown.class;
        }

        @Override
        public EventType listenerType() {
            return EventType.ALL;
        }
    }

    private class CountDownCreateListener extends CountDownAllListener {

        @Override
        public EventType listenerType() {
            return EventType.CREATE;
        }
    }

    private class CountDownUpdateListener extends CountDownAllListener {

        @Override
        public EventType listenerType() {
            return EventType.UPDATE;
        }
    }

    private class CountDownDeleteListener extends CountDownAllListener {

        @Override
        public EventType listenerType() {
            return EventType.DELETE;
        }
    }

}
