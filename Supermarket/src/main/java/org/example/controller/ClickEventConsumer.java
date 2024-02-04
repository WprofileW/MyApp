package org.example.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ClickEventConsumer {

    @KafkaListener(topics = "click-events")
    public void processClickEvent(ConsumerRecord<String, String> record) {
        String[] parts = record.value().split(",");
        String userId = parts[0];
        String pageId = parts[1];

        // 在这里执行点击次数统计的业务逻辑，例如存储到数据库或内存中
        System.out.println("Click Event Received - User: " + userId + ", Page: " + pageId);
    }
}
