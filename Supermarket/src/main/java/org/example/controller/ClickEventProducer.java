package org.example.controller;

import com.alibaba.fastjson.JSON;
import org.example.pojo.ClickEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ClickEventProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendClickEvent(ClickEvent clickEvent) {
        clickEvent.setStartTime(LocalDateTime.now().
                format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        kafkaTemplate.send("click-events", JSON.toJSONString(clickEvent));
    }
}