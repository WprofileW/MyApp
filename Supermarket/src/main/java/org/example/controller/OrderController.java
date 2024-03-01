package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.OrderItem;
import org.example.pojo.Result;
import org.example.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@CrossOrigin
@Validated
@Slf4j
public class OrderController {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/addOrderItem")
    public Result addOrderItem(@RequestBody List<OrderItem> orderItemList) {
        return orderItemService.addOrderItem(orderItemList);
    }

    @PutMapping("/updateOrderItem")
    public Result updateOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.updateOrderItem(orderItem);
    }

    @PostMapping("/deleteOrderItem")
    public Result deleteOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.deleteOrderItem(orderItem);
    }

    @PostMapping("/getAllOrders")
    public <T> Result getAllOrders(@RequestBody Map<T, T> params) {
        return orderItemService.getAllOrders(params);
    }
}
