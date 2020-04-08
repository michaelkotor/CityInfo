package com.kotor.config;

import java.io.InputStream;
import java.util.Properties;

public class BotConfig {

    private String username;
    private String token;

    public static BotConfig getInstance() {
        return new BotConfig();
    }

    private BotConfig() {
        try (InputStream input = BotConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            this.token = properties.getProperty("token");
            this.username = properties.getProperty("username");
        } catch (Exception e) {
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
