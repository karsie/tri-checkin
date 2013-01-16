package com.tricode.checkin.event.listener;

import com.tricode.checkin.event.EventType;
import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.Person;
import com.tricode.checkin.model.StatusChangeLog;
import com.tricode.checkin.service.LogService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PersonUpdateListenerTest {

    private PersonUpdateListener listener;

    @Mock
    private LogService logService;

    @Before
    public void setUp() throws Exception {
        listener = new PersonUpdateListener(logService);
    }

    @Test
    public void whenStatusChangesThenLogIsCreated() throws Exception {
        final int userId = 1;
        final Person a = Person.Builder.withId(userId).withStatus(LocationStatus.IN).get();
        final Person b = Person.Builder.withId(userId).withStatus(LocationStatus.OUT).get();

        listener.onEvent(a, b, EventType.UPDATE);

        final ArgumentCaptor<StatusChangeLog> capture = ArgumentCaptor.forClass(StatusChangeLog.class);
        verify(logService).addStatusChange(capture.capture());

        final StatusChangeLog argument = capture.getValue();
        assertThat(argument.getUserId(), is(equalTo(userId)));
        assertThat(argument.getStatusFrom(), is(equalTo(LocationStatus.IN)));
        assertThat(argument.getStatusTo(), is(equalTo(LocationStatus.OUT)));
    }

    @Test
    public void whenStatusDoesNotChangeThenNoLogIsCreated() throws Exception {
        final int userId = 1;
        final Person a = Person.Builder.withId(userId).withStatus(LocationStatus.OUT).get();
        final Person b = Person.Builder.withId(userId).withStatus(LocationStatus.OUT).get();

        listener.onEvent(a, b, EventType.UPDATE);

        verify(logService, never()).addStatusChange(isA(StatusChangeLog.class));
    }
}
