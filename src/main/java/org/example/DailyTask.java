package org.example;

import java.io.IOException;

import org.example.config.EnvLoader;
import org.example.gptClient.GptClient;
import org.example.gptClient.Prompt;
import org.example.mailModule.EmailSender;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DailyTask implements Job {    
    private static final Logger logger = LoggerFactory.getLogger(DailyTask.class);
    
    @Override    
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
        logger.info("Start execute {}", this.getClass());
        String recipient = EnvLoader.getRecipient();
        String subject = "English vocabulary";
        String response = null;
        try {
            GptClient client = new GptClient();
            logger.debug("Before gpt - in try");
            response = client.getResponse(Prompt.prompt);
            logger.debug("After gpt - in try");
        } catch (IOException e) {
            logger.debug("Problem - in catch" + e);
            throw new RuntimeException(e);
        }

        EmailSender.sendEmail(recipient, subject, response);
    }
}
