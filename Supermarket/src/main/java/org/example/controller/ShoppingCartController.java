package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.ShoppingCartItem;
import org.example.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/shoppingCart")
@CrossOrigin
@Validated
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/addCartItem")
    public Result addCartItem(@RequestBody ShoppingCartItem shoppingCartItem) {
        return shoppingCartService.addCartItem(shoppingCartItem);
    }

    @PutMapping("/updateCartItem")
    public Result updateCartItem(@RequestBody ShoppingCartItem shoppingCartItem) {
        return shoppingCartService.updateCartItem(shoppingCartItem);
    }

    @PostMapping("/deleteCartItem")
    public Result deleteCartItem(@RequestBody ShoppingCartItem shoppingCartItem) {
        return shoppingCartService.deleteCartItem(shoppingCartItem);
    }

    @PostMapping("/getAllCartItems")
    public <T> Result getAllCartItems(@RequestBody Map<T, T> params) {
        return shoppingCartService.getAllCartItems(params);
    }
}
