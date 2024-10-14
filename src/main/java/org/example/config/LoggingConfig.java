package org.example.config;

public class LoggingConfig {
    private String debugLevel;

    public LoggingConfig(String[] args) {
        if (args.length > 0) {
            debugLevel = args[0];
        } else {
            debugLevel = System.getenv("DEBUG_LEVEL");
        }

        if (debugLevel != null) {
            System.setProperty("DEBUG_LOG_LEVEL", debugLevel);
        } else {
            System.setProperty("DEBUG_LOG_LEVEL", "ERROR");
        }
    }

    public String getDebugLevel() {
        return debugLevel;
    }
    
}
