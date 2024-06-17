package org.xwstxkx.telegrambot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.xwstxkx.telegrambot.service.CommandsService;

@Slf4j
@Component
public class DelFinBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    private static final String START = "/start";
    private static final String HELP = "/help";
    private final CommandsService commandsService;

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        var message = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();
        var username = update.getMessage().getChat().getUserName();
        switch (message) {
            case START -> {
                sendMessage(chatId,
                        commandsService.startCommand(username));
            }
            case HELP -> {

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
        return botName;
    }

    public DelFinBot(@Value("${bot.token}") String botToken,
                     CommandsService commandsService) {
        super(botToken);
        this.commandsService = commandsService;
    }
}
