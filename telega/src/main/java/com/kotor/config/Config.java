package com.kotor.config;

import com.kotor.bot.CityBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Configuration
public class Config {

    @Value("token")
    private String token;

    @Value("username")
    private String username;

    @Bean
    public BotConfig botConfig() {
        return null;
    }

    @Bean
    public void start() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new CityBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
