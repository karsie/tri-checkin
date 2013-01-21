package com.tricode.checkin.event.listener;

import com.tricode.checkin.event.EventType;
import com.tricode.checkin.model.LocationStatus;
import com.tricode.checkin.model.StatusChangeLog;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.service.LogService;
import com.tricode.checkin.service.ReportingService;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StatusChangeLogCreateListenerTest {

    private StatusChangeLogCreateListener listener;

    @Mock
    private LogService logService;

    @Mock
    private ReportingService reportingService;

    @Before
    public void setUp() throws Exception {
        listener = new StatusChangeLogCreateListener(reportingService, logService);
    }

    @Test
    public void whenCheckinLogCreatedNoReportIsUpdated() throws Exception {
        final StatusChangeLog checkinLog = StatusChangeLog.Builder.withUserId(1).withStatuses(LocationStatus.OUT, LocationStatus.IN).get();

        listener.onEvent(null, checkinLog, EventType.CREATE);

        verify(reportingService, never()).save(isA(WeekReport.class));
    }

    @Test
    public void whenCheckoutLogCreatedOnSundayThenReportForMondayIsUpdated() throws Exception {
        int userId = 1;

        DateTime checkoutTime = new DateTime().withHourOfDay(17).withDayOfWeek(7);
        DateTime checkinTime = checkoutTime.minusHours(8);

        final StatusChangeLog checkoutLog = StatusChangeLog.Builder.withUserId(userId).withStatuses(LocationStatus.IN,
                LocationStatus.OUT)
                .withTimestamp(checkoutTime.getMillis())
                .get();

        final StatusChangeLog checkinLog = StatusChangeLog.Builder.withUserId(userId).withStatuses(LocationStatus.OUT,
                LocationStatus.IN)
                .withTimestamp(checkinTime.getMillis())
                .get();

        final WeekReport weekReport = WeekReport.Builder.withUserId(userId).withYear(checkoutTime.getWeekyear()).withWeek(checkoutTime.getWeekOfWeekyear()).get();

        when(logService.getLastStatusChangeForUser(userId, LocationStatus.IN)).thenReturn(checkinLog);
        when(reportingService.get(userId, checkoutTime.getWeekyear(), checkoutTime.getWeekOfWeekyear())).thenReturn(weekReport);

        listener.onEvent(null, checkoutLog, EventType.CREATE);

        final ArgumentCaptor<WeekReport> capture = ArgumentCaptor.forClass(WeekReport.class);
        verify(reportingService).save(capture.capture());

        final WeekReport argument = capture.getValue();
        assertThat(argument.getDays().size(), is(equalTo(5)));
        assertThat(argument.getDays().get(0), is(greaterThanOrEqualTo(8L * 3600 * 1000)));
        assertThat(argument.getDays().get(1), is(equalTo(0L)));
        assertThat(argument.getDays().get(2), is(equalTo(0L)));
        assertThat(argument.getDays().get(3), is(equalTo(0L)));
        assertThat(argument.getDays().get(4), is(equalTo(0L)));
    }

    @Test
    public void whenCheckoutLogCreatedOnSaturdayThenReportForFridayIsUpdated() throws Exception {
        int userId = 1;

        DateTime checkoutTime = new DateTime().withHourOfDay(17).withDayOfWeek(6);
        DateTime checkinTime = checkoutTime.minusHours(8);

        final StatusChangeLog checkoutLog = StatusChangeLog.Builder.withUserId(userId).withStatuses(LocationStatus.IN,
                LocationStatus.OUT)
                .withTimestamp(checkoutTime.getMillis())
                .get();

        final StatusChangeLog checkinLog = StatusChangeLog.Builder.withUserId(userId).withStatuses(LocationStatus.OUT,
                LocationStatus.IN)
                .withTimestamp(checkinTime.getMillis())
                .get();

        final WeekReport weekReport = WeekReport.Builder.withUserId(userId).withYear(checkoutTime.getWeekyear()).withWeek(checkoutTime.getWeekOfWeekyear()).get();

        when(logService.getLastStatusChangeForUser(userId, LocationStatus.IN)).thenReturn(checkinLog);
        when(reportingService.get(userId, checkoutTime.getWeekyear(), checkoutTime.getWeekOfWeekyear())).thenReturn(weekReport);

        listener.onEvent(null, checkoutLog, EventType.CREATE);

        final ArgumentCaptor<WeekReport> capture = ArgumentCaptor.forClass(WeekReport.class);
        verify(reportingService).save(capture.capture());

        final WeekReport argument = capture.getValue();
        assertThat(argument.getDays().size(), is(equalTo(5)));
        assertThat(argument.getDays().get(0), is(equalTo(0L)));
        assertThat(argument.getDays().get(1), is(equalTo(0L)));
        assertThat(argument.getDays().get(2), is(equalTo(0L)));
        assertThat(argument.getDays().get(3), is(equalTo(0L)));
        assertThat(argument.getDays().get(4), is(greaterThanOrEqualTo(8L * 3600 * 1000)));
    }
}
