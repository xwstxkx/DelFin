package org.xwstxkx.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.xwstxkx.kafka.KafkaProducer;

@RestController
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("kafka/send")
    public String send(@RequestBody String message) {
        kafkaProducer.sendMessage(message);
        return "Success";
    }
}
