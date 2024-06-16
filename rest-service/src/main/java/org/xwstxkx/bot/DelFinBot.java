//package org.xwstxkx.bot;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import org.xwstxkx.service.CommandService;
//
//@Slf4j
//@Component
//public class DelFinBot extends TelegramLongPollingBot {
//
//    private static final String START = "/start";
//    private static final String DEBTS = "/debts";
//    private static final String LOANS = "/loans";
//    private static final String EXPENSES = "/expenses";
//    private static final String INCOMES = "/incomes";
//    private static final String BUDGETS = "/budgets";
//    @Autowired
//    private CommandService commandService;
//
//    public DelFinBot(@Value("$bot.token") String botToken) {
//        super(botToken);
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        if (!update.hasMessage() || !update.getMessage().hasText()) {
//            return;
//        }
//        var message = update.getMessage().getText();
//        var chatId = update.getMessage().getChatId();
//        switch (message) {
//            case START -> {
//                sendMessage(
//                        chatId, commandService.startCommand(
//                                update.getMessage()
//                                .getChat().getUserName()
//                        )
//                );
//            }
//        }
//    }
//
//    public void sendMessage(Long chatId, String text) {
//        var chatIdStr = String.valueOf(chatId);
//        var sendMessage = new SendMessage(chatIdStr, text);
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException exception) {
//            log.error("Ошибка отправки сообщения", exception);
//        }
//    }
//
//    @Override
//    public String getBotUsername() {
//        return null;
//    }
//
//}
