package org.xwstxkx.telegrambot.services.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.xwstxkx.telegrambot.controller.UpdateController;
import org.xwstxkx.telegrambot.services.AnswerConsumer;

import static org.xwstxkx.rabbitmq.model.RabbitQueue.ANSWER_MESSAGE_UPDATE;

@Service
public class AnswerConsumerImpl implements AnswerConsumer {

    private final UpdateController updateController;

    public AnswerConsumerImpl(UpdateController updateController) {
        this.updateController = updateController;
    }

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE_UPDATE)
    public void consume(SendMessage message) {
        updateController.setView(message);
    }
}
