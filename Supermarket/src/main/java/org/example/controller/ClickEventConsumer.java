package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

// Kafka 消费者
@Slf4j
@Component
public class ClickEventConsumer {
    @KafkaListener(topics = "click-events")    public void consume(String clickEvent) {
        // 处理点击事件，例如计算点击率、生成报告等
        log.info("[CustomizedLogs]:Received click event:{}", clickEvent);
        System.out.println("Received click event: " + clickEvent);
    }
}