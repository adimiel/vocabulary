package org.example.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {
    private static Dotenv dotenv = Dotenv.load();

    public static String getBroadcaster() {
        return dotenv.get("BROADCASTER");
    }
    public static String getRecipient() {
        return dotenv.get("RECIPIENT");
    }

    public static String getEmailPassword() {
        return dotenv.get("EMAIL_PASSWORD");
    }

    public static String getApiUrl() {
        return dotenv.get("API_URL");
    }

    public static String getApiKey() {
        return dotenv.get("API_KEY");
    }

}
