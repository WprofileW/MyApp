package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clickEvent")
@Validated
public class ClickController {
    @Autowired
    private ClickEventProducer clickEventProducer;

    @GetMapping("/click")
    public String handleClickEvent(@RequestParam String userId, @RequestParam String pageId) {
        // 处理点击事件的业务逻辑

        // 发送点击事件到Kafka
        clickEventProducer.sendClickEvent(userId, pageId);

        return "Click event processed successfully!";
    }
}


