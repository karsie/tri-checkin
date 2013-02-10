package com.tricode.checkin.event.listener;

import com.tricode.checkin.event.EventType;
import com.tricode.checkin.model.EatingInLog;
import com.tricode.checkin.model.MonthReport;
import com.tricode.checkin.model.UserReport;
import com.tricode.checkin.model.WeekReport;
import com.tricode.checkin.service.LogService;
import com.tricode.checkin.service.ReportingService;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EatingInLogCreatedListenerTest {

    private EatingInLogCreatedListener listener;

    @Mock
    private ReportingService reportingService;

    @Before
    public void setUp() throws Exception {
        listener = new EatingInLogCreatedListener(reportingService);
    }

    @Test
    public void whenEatingInLogCreatedThenWeekAndMonthReportAreUpdated() throws Exception {
        int userId = 1;

        DateTime logTime = new DateTime().withHourOfDay(17).withDayOfWeek(4);

        final EatingInLog eatingInLog = EatingInLog.Builder.withUserId(userId).withEatingIn(true)
                .withTimestamp(logTime.getMillis())
                .get();

        final WeekReport weekReport = WeekReport.Builder.withUserId(userId).get();
        final MonthReport monthReport = MonthReport.Builder.withUserId(userId).get();

        doReturn(weekReport).when(reportingService).getWeek(userId, logTime.getWeekyear(), logTime.getWeekOfWeekyear());
        doReturn(monthReport).when(reportingService).getMonth(userId, logTime.getYear(), logTime.getMonthOfYear());

        listener.onEvent(null, eatingInLog, EventType.CREATE);

        final ArgumentCaptor<UserReport> capture = ArgumentCaptor.forClass(UserReport.class);

        verify(reportingService, times(2)).save(capture.capture());

        for (UserReport userReport : capture.getAllValues()) {
            if (userReport instanceof WeekReport) {
                final WeekReport weekArg = (WeekReport) userReport;
                assertThat(weekArg.getEatingIn().get(logTime.getDayOfWeek() - 1), is(equalTo(true)));
            } else if (userReport instanceof MonthReport) {
                final MonthReport monthArg = (MonthReport) userReport;
                assertThat(monthArg.getEatingIn().get(logTime.getDayOfMonth() - 1), is(equalTo(true)));
            }
        }
    }

    @Test
    @Ignore("doesn't work yet")
    public void whenEatingOutLogCreatedThenWeekAndMonthReportAreUpdated() throws Exception {
        int userId = 1;

        DateTime logTime = new DateTime().withHourOfDay(17).withDayOfWeek(3);

        final EatingInLog eatingOutLog = EatingInLog.Builder.withUserId(userId).withEatingIn(false)
                .withTimestamp(logTime.getMillis())
                .get();

        listener.onEvent(null, eatingOutLog, EventType.CREATE);

        verify(reportingService, times(2)).save(isA(WeekReport.class));
    }

    @Test
    public void whenEatingInLogCreatedOnSundayThenNothingIsUpdated() throws Exception {
        int userId = 1;

        DateTime checkoutTime = new DateTime().withHourOfDay(17).withDayOfWeek(7);

        final EatingInLog checkoutLog = EatingInLog.Builder.withUserId(userId).withEatingIn(true)
                .withTimestamp(checkoutTime.getMillis())
                .get();

        listener.onEvent(null, checkoutLog, EventType.CREATE);

        verify(reportingService, never()).save(isA(WeekReport.class));
    }

    @Test
    public void whenEatingInLogCreatedOnSaturdayThenNothingIsUpdated() throws Exception {
        int userId = 1;

        DateTime checkoutTime = new DateTime().withHourOfDay(17).withDayOfWeek(6);

        final EatingInLog checkoutLog = EatingInLog.Builder.withUserId(userId).withEatingIn(true)
                .withTimestamp(checkoutTime.getMillis())
                .get();

        listener.onEvent(null, checkoutLog, EventType.CREATE);

        verify(reportingService, never()).save(isA(WeekReport.class));
    }
}
