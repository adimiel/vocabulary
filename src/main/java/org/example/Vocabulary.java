package org.example;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.example.config.LoggingConfig;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class Vocabulary {
    
    private static final Logger logger = LoggerFactory.getLogger(Vocabulary.class);

    public static void main(String[] args) throws IOException {
        LoggingConfig loggingConfig = new LoggingConfig(args);
        String currentDebugLevel = loggingConfig.getDebugLevel();
        System.out.println("Current debug level is: " + currentDebugLevel);

        try {
            logger.info("Application started");
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDetail job = JobBuilder.newJob(DailyTask.class)
                    .withIdentity("dailyJob", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("dailyTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))
                    .build();

            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}