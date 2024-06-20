package org.xwstxkx.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.xwstxkx.dao.AppUserDao;
import org.xwstxkx.dao.RawDataDao;
import org.xwstxkx.entity.AppUser;
import org.xwstxkx.entity.RawData;
import org.xwstxkx.service.MainService;
import org.xwstxkx.service.ProducerService;

import java.util.Optional;

import static org.xwstxkx.entity.UserState.BASIC_STATE;
import static org.xwstxkx.entity.UserState.WAIT_FOR_EMAIL_STATE;
import static org.xwstxkx.service.enums.ServiceCommands.*;

@Log4j
@Service
public class MainServiceImpl implements MainService {
    private final RawDataDao rawDataDao;
    private final ProducerService producerService;
    private final AppUserDao appUserDao;

    public MainServiceImpl(
            RawDataDao rawDataDao, ProducerService producerService,
            AppUserDao appUserDao
    ) {
        this.rawDataDao = rawDataDao;
        this.producerService = producerService;
        this.appUserDao = appUserDao;
    }

    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var userState = appUser.getUserState();
        var text = update.getMessage().getText();
        var output = "";

        if (CANCEL.equals(text)) {
            output = cancelProcess(appUser);
        } else if (BASIC_STATE.equals(userState)) {
            output = processServiceCommand(appUser, text);
        } else if (WAIT_FOR_EMAIL_STATE.equals(userState)) {
            //TODO подтверждение через электронную почту
        } else {
            log.error("Неизвестное состояние пользователя: " + userState);
            output = "Неизвестная ошибка! Введите /cancel и попробуйте снова!";
        }

        var chatId = update.getMessage().getChatId();
        sendAnswer(output, chatId);
    }

    @Override
    public void processPhotoMessage(Update update) {
        //TODO здесь будет сканирование чека или анализ другой иной информации из изображений
    }

    private boolean isNotAllowToSendContent(Long chatId, AppUser appUser) {
        var userState = appUser.getUserState();
        if (!appUser.getIsActive()) {
            var error = "Зарегистрируйтесь или активируйте свою учётную запись" +
                    "для отправки контента";
            sendAnswer(error, chatId);
            return true;
        } else if (!BASIC_STATE.equals(userState)) {
            var error = "Отмените текущую команду с помощью /cancel " +
                    "для отправки контента.";
            sendAnswer(error, chatId);
            return true;
        }
        return false;
    }

    private void sendAnswer(String output, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(output);
        producerService.produceAnwer(sendMessage);
    }

    private String processServiceCommand(AppUser appUser, String cmd) {
        if (REGISTRATION.equals(cmd)) {
            //TODO добавить регистрацию
            return "Временно недоступно.";
        } else if (HELP.equals(cmd)) {
            return help();
        } else if (START.equals(cmd)) {
            return "Приветствую! Чтобы посмотреть список доступных команд введите /help";
        } else {
            return "Неизвестная команда! Чтобы посмотреть список доступных команд введите /help";
        }
    }

    private String help() {
        return "Список доступных команд:\n" +
                "/cancel - отмена выполнения текущей команды;\n" +
                "/registration - регистрация пользователя.";
    }

    private String cancelProcess(AppUser appUser) {
        appUser.setUserState(BASIC_STATE);
        appUserDao.save(appUser);
        return "Команда отменена!";
    }

    private AppUser findOrSaveAppUser(Update update) {
        var telegramUser = update.getMessage().getFrom();
        AppUser persistentUser = appUserDao.findByTelegramUserId(telegramUser.getId());
        if (persistentUser == null) {
            AppUser transientUser = AppUser.builder()
                    .telegramUserId(telegramUser.getId())
                    .username(telegramUser.getUserName())
                    .firstname(telegramUser.getFirstName())
                    .lastname(telegramUser.getLastName())
                    //TODO Заменить значение по умолчанию после добавления регистрации по почте
                    .isActive(false)
                    .build();
            return appUserDao.save(transientUser);
        }
        return persistentUser;
    }

    private void saveRawData(Update update) {
        rawDataDao.save(RawData.builder()
                .event(update)
                .build());
    }
}
