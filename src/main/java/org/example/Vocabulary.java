package org.example;

import org.example.gptClient.GptClient;
import org.example.gptClient.Prompt;
import org.example.mailModule.EmailSender;
import org.example.config.EnvLoader;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import static org.example.mailModule.EmailScheduler.scheduleDailyEmail;

@Slf4j
public class Vocabulary {
    public static void main(String[] args) throws IOException {

        log.info("Application started.");


        Runnable emailTask = () -> {
            log.info("Starting email sending task.");
            String recipient = EnvLoader.getRecipient();
            String subject = "English vocabulary";
            String response = null;
            try {
                GptClient client = new GptClient();
                log.info("Before gpt - in try");
                response = client.getResponse(Prompt.prompt);
                log.info("Afrer gpt - in try");
            } catch (IOException e) {
                log.info("Problem - in catch" + e);
                throw new RuntimeException(e);
            }

            EmailSender.sendEmail(recipient, subject, response);
        };

        scheduleDailyEmail(emailTask);

    }
}