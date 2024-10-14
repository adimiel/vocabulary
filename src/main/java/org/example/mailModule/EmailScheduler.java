package org.example.mailModule;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailScheduler.class);

    public static long getInitialDelay() {
        Calendar currentTime = Calendar.getInstance();
        Calendar nextRunTime = Calendar.getInstance();

        nextRunTime.set(Calendar.HOUR_OF_DAY, 6);
        nextRunTime.set(Calendar.MINUTE, 0);
        nextRunTime.set(Calendar.SECOND, 0);

        if (nextRunTime.before(currentTime)) {
            nextRunTime.add(Calendar.DAY_OF_MONTH, 1);
        }

        logger.info("Email is scheduled at: " + nextRunTime.getTime());

        return nextRunTime.getTimeInMillis() - currentTime.getTimeInMillis();
    }

    public static void scheduleDailyEmail(Runnable emailTask) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        long initialDelay = getInitialDelay();
        long period = TimeUnit.DAYS.toMillis(1);

        logger.info("Scheduling daily email task");

        scheduler.scheduleAtFixedRate(emailTask, initialDelay, period, TimeUnit.MILLISECONDS);
    }

}
