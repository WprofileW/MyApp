package org.example.controller;

import org.example.pojo.ProductInventory;
import org.example.pojo.Result;
import org.example.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/addProductInventory")
    public Result addProduct(@RequestBody ProductInventory productInventory) {
        return productInventoryService.addProductInventory(productInventory);
    }


    @PostMapping("/getAllProducts")
    public <T> Result getAllUsers(@RequestBody Map<T, T> params) {
        return productInventoryService.getAllProducts(params);
    }
}
