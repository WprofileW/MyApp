package org.example.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.pojo.ProductInventory;
import org.example.pojo.Result;
import org.example.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/product")
@CrossOrigin
@Validated
@Slf4j
public class ProductInventoryController {
    @Autowired
    private ProductInventoryService productInventoryService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private Map<Object, Object> productInventoryMap;

    @PostMapping("/addProduct")
    public Result addProduct(@RequestBody ProductInventory productInventory) {
        kafkaTemplate.send("productInventoryEvent", JSON.toJSONString(productInventory));
        log.info("[CustomizedLogs]:productInventoryEvent:{}", JSON.toJSONString(productInventory));
        return productInventoryService.addProduct(productInventory);
    }

    @PutMapping("/updateProduct")
    public Result updateProduct(@RequestBody ProductInventory productInventory) {
        kafkaTemplate.send("productInventoryEvent", JSON.toJSONString(productInventory));
        log.info("[CustomizedLogs]:productInventoryEvent:{}", JSON.toJSONString(productInventory));
        return productInventoryService.updateProduct(productInventory);
    }

    @PutMapping("/updateProductByName")
    public Result updateProductByName(@RequestBody ProductInventory productInventory) {
        kafkaTemplate.send("productInventoryEvent", JSON.toJSONString(productInventory));
        log.info("[CustomizedLogs]:productInventoryEvent:{}", JSON.toJSONString(productInventory));
        return productInventoryService.updateProductByName(productInventory);
    }

    @PostMapping("/deleteProduct")
    public Result deleteProduct(@RequestBody ProductInventory productInventory) {
        return productInventoryService.deleteProduct(productInventory);
    }

    @PostMapping("/getAllProducts")
    public <T> Result getAllProducts(@RequestBody Map<T, T> params) {
        return productInventoryService.getAllProducts(params);
    }

    @PostMapping("/getProductByName")
    public <T> Result getProductByName(@RequestBody Map<T, T> params) {
        return productInventoryService.getProductByName(params);
    }
}