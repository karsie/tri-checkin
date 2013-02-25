package com.tricode.checkin.task;

import org.springframework.util.StopWatch;

public final class StopWatchHelper {

    private final StopWatch stopWatch;

    private StopWatchHelper() {
        stopWatch = new StopWatch();
        stopWatch.start();
    }

    public static StopWatchHelper start() {
        return new StopWatchHelper();
    }

    public long stopAndGetMillis() {
        stopWatch.stop();
        return stopWatch.getTotalTimeMillis();
    }
}
