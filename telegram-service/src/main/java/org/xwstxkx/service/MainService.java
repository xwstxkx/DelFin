package org.xwstxkx.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.xwstxkx.exceptions.ObjectNotFound;

public interface MainService {
    void processTextMessage(Update update) throws ObjectNotFound;
    void processPhotoMessage(Update update);
}
