package org.xwstxkx.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.xwstxkx.exceptions.ObjectNotFound;

public interface ConsumerService {
    void consumeTextMessageUpdates(Update update) throws ObjectNotFound;
    void consumePhotoMessageUpdates(Update update);
}
