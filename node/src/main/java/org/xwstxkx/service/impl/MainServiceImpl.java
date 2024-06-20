package org.xwstxkx.service.impl;

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
import static org.xwstxkx.service.enums.ServiceCommands.CANCEL;

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
        }

        var message = update.getMessage();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Привет из NODE");
        producerService.produceAnwer(sendMessage);
    }

    private String processServiceCommand(AppUser appUser, String text) {
    }

    private String cancelProcess(AppUser appUser) {
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
