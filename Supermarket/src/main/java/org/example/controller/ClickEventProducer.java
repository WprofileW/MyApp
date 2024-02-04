package org.example.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClickEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ClickEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendClickEvent(String userId, String pageId) {
        String topic = "click-events";
        String message = userId + "," + pageId;
        kafkaTemplate.send(topic, message);
    }
}
