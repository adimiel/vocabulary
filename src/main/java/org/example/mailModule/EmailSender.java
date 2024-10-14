package org.example.mailModule;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.example.config.EnvLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class EmailSender {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    private static Session createSession() {

        String host = "smtp.gmail.com";
        final String from = EnvLoader.getBroadcaster();
        final String password = EnvLoader.getEmailPassword();

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
    }

    public static void sendEmail(String to, String subject, String content) {

        Session session = createSession();
        logger.debug("Email session created");

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EnvLoader.getBroadcaster()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            logger.info("Email sent successfully to {}", to);


        } catch (MessagingException e) {
            logger.error("Failed to send email to {}: ", to, e);
        }
    }
}
