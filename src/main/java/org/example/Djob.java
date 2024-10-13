package org.example;

import java.io.IOException;

import org.example.config.EnvLoader;
import org.example.gptClient.GptClient;
import org.example.gptClient.Prompt;
import org.example.mailModule.EmailSender;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Djob implements Job {
    
    private static final Logger logger = LoggerFactory.getLogger(Djob.class);
    @Override    
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
        logger.debug("Its a debug log in Djab");
        String recipient = EnvLoader.getRecipient();
        String subject = "English vocabulary";
        String response = null;
        try {
            GptClient client = new GptClient();
            log.info("Before gpt - in try");
            response = client.getResponse(Prompt.prompt);
            log.info("After gpt - in try");
        } catch (IOException e) {
            log.info("Problem - in catch" + e);
            throw new RuntimeException(e);
        }

        EmailSender.sendEmail(recipient, subject, response);
    }
}
