package org.example;

import org.example.gptClient.GptClient;
import org.example.gptClient.Prompt;
import org.example.mailModule.EmailSender;
import org.example.config.EnvLoader;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import static org.example.mailModule.EmailScheduler.scheduleDailyEmail;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {

        log.info("Application started.");

        GptClient client = new GptClient();

        Runnable emailTask = () -> {
            log.info("Starting email sending task.");
            String recipient = EnvLoader.getRecipient();
            String subject = "English vocabulary";
            String response = null;
            try {
                response = client.getResponse(Prompt.prompt);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            EmailSender.sendEmail(recipient, subject, response);
        };

        scheduleDailyEmail(emailTask);


    }
}