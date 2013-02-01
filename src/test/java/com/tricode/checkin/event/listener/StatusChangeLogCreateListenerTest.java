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
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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
    public void whenCheckoutLogCreatedOnSundayThenNothingIsUpdated() throws Exception {
        int userId = 1;

        DateTime checkoutTime = new DateTime().withHourOfDay(17).withDayOfWeek(7);

        final StatusChangeLog checkoutLog = StatusChangeLog.Builder.withUserId(userId).withStatuses(LocationStatus.IN,
                                                                                                    LocationStatus.OUT)
                .withTimestamp(checkoutTime.getMillis())
                .get();

        listener.onEvent(null, checkoutLog, EventType.CREATE);

        verify(reportingService, never()).save(isA(WeekReport.class));
    }

    @Test
    public void whenCheckoutLogCreatedOnSaturdayThenNothingIsUpdated() throws Exception {
        int userId = 1;

        DateTime checkoutTime = new DateTime().withHourOfDay(17).withDayOfWeek(6);

        final StatusChangeLog checkoutLog = StatusChangeLog.Builder.withUserId(userId).withStatuses(LocationStatus.IN,
                LocationStatus.OUT)
                .withTimestamp(checkoutTime.getMillis())
                .get();

        listener.onEvent(null, checkoutLog, EventType.CREATE);

        verify(reportingService, never()).save(isA(WeekReport.class));
    }
}
