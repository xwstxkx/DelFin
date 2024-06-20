package org.xwstxkx.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.xwstxkx.service.ConsumerService;

import static org.xwstxkx.rabbitmq.model.RabbitQueue.PHOTO_MESSAGE_UPDATE;
import static org.xwstxkx.rabbitmq.model.RabbitQueue.TEXT_MESSAGE_UPDATE;

@Log4j
@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final MainServiceImpl mainService;

    public ConsumerServiceImpl(MainServiceImpl mainService) {
        this.mainService = mainService;
    }

    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessageUpdates(Update update) {
        log.debug("NODE: сообщение было доставлено");
        mainService.processTextMessage(update);
    }

    @Override
    @RabbitListener(queues = PHOTO_MESSAGE_UPDATE)
    public void consumePhotoMessageUpdates(Update update) {
        log.debug("NODE: сообщение было доставлено");
    }
}
