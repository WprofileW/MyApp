package org.example.controller;

import org.example.pojo.Result;
import org.example.service.ProductInventoryChangeService;
import org.example.service.TopProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chart")
@CrossOrigin
public class ChartController {
    @Autowired
    private ProductInventoryChangeService productInventoryChangeService;
    @Autowired
    private TopProductService topProductService;

    @GetMapping("/getInventoryChangeByMinute")
    public Result getInventoryChangeByMinute() {
        return productInventoryChangeService.getInventoryChangeByMinute();
    }

    @GetMapping("/getInventoryChangeLimitFive")
    public Result getInventoryChangeLimitFive() {
        return productInventoryChangeService.getInventoryChangeLimitFive();
    }

    @GetMapping("/getTopThreeSale")
    public Result getTopThreeSale() {
        return topProductService.getTopThreeSale();
    }
}