package org.example;

// import org.example.gptClient.GptClient;
// import org.example.gptClient.Prompt;
// import org.example.mailModule.EmailSender;
// import org.example.config.EnvLoader;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

// import static org.example.mailModule.EmailScheduler.scheduleDailyEmail;

public class Vocabulary {
    
    private static final Logger logger = LoggerFactory.getLogger(Djob.class);

    public static void main(String[] args) throws IOException {
        try {
            logger.debug("First debug log");
            logger.info("First info log");
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDetail job = JobBuilder.newJob(Djob.class)
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