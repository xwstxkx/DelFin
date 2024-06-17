package org.xwstxkx.telegrambot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.xwstxkx.telegrambot.controller.DelFinBot;

@Data
@Configuration
public class BotConfiguration {

    @Value("${bot.name}")
    private String name;
    @Value("${bot.token}")
    private String token;

    @Bean
    public TelegramBotsApi telegramBotsApi(
            DelFinBot delFinBot
    ) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(delFinBot);
        return api;
    }

}
