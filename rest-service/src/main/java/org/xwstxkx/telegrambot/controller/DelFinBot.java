package org.xwstxkx.telegrambot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.xwstxkx.telegrambot.config.BotConfiguration;
import org.xwstxkx.telegrambot.service.CommandsService;

@Slf4j
@Component
@RequiredArgsConstructor
public class DelFinBot extends TelegramLongPollingBot {

    private final BotConfiguration configuration;
    private final CommandsService commandsService;

    private static final String START = "/start";
    private static final String BUDGETS = "/budgets";
    private static final String HELP = "/help";

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        var message = update.getMessage();
        String messageText = message.getText();
        Long chatId = message.getChatId();
        var username = message.getChat().getUserName();
        Long userId = message.getChat().getId();
        switch (messageText) {
            case START -> {
                sendMessage(chatId,
                        commandsService.startCommand(username, userId));
            }
            case BUDGETS -> {
//                sendMessage(chatId,
//                        commandsService.budgetsCommand());
            }
            case HELP -> {
                sendMessage(chatId,
                        commandsService.helpCommand());
            }
        }
    }

    private void sendMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }

    @Override
    public String getBotUsername() {
        return configuration.getName();
    }

    @Override
    public String getBotToken() {
        return configuration.getToken();
    }
}
