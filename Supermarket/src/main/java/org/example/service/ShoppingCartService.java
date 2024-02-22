package org.example.service;

import org.example.pojo.Result;
import org.example.pojo.ShoppingCartItem;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ShoppingCartService {
    Result addCartItem(ShoppingCartItem shoppingCartItem);

    Result updateCartItem(ShoppingCartItem shoppingCartItem);

    Result deleteCartItem(ShoppingCartItem shoppingCartItem);

    <T> Result getAllCartItems(Map<T, T> params);

    Result deleteAllCartItems();
}
