package org.example.controller;

import org.example.pojo.ClickEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 控制器
@RestController
@RequestMapping("/click")
public class ClickController {
    @Autowired
    private ClickEventProducer clickEventProducer;

    @PostMapping("/event")
    public ResponseEntity<String> handleClick(@RequestBody ClickEvent clickEvent) {
        // 处理点击事件
        clickEventProducer.sendClickEvent(clickEvent);
        return ResponseEntity.ok("Click event received and processed successfully.");
    }
}