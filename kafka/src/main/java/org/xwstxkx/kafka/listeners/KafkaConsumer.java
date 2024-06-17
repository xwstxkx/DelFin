package org.xwstxkx.kafka.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "delfin", groupId = "groupId")
    public void listen(String message) {
        log.info("Received message: " + message);
    }
}
