package org.example.controller;

import com.alibaba.fastjson.JSON;
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
public class ProductInventoryController {
    @Autowired
    private ProductInventoryService productInventoryService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/addProduct")
    public Result addProduct(@RequestBody ProductInventory productInventory) {
        kafkaTemplate.send("InventoryChange", JSON.toJSONString(productInventory));
        return productInventoryService.addProductInventory(productInventory);
    }

    @PutMapping("/updateProduct")
    public Result updateProduct(@RequestBody ProductInventory productInventory) {
        return productInventoryService.updateProduct(productInventory);
    }

    @PostMapping("/deleteProduct")
    public Result deleteProduct(@RequestBody ProductInventory productInventory) {
        return productInventoryService.deleteProduct(productInventory);
    }

    @PostMapping("/getAllProducts")
    public <T> Result getAllProducts(@RequestBody Map<T, T> params) {
        return productInventoryService.getAllProducts(params);
    }
}
