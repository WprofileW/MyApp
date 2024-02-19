package org.example.service;

import org.example.pojo.ProductInventory;
import org.example.pojo.Result;

import java.util.Map;

public interface ProductInventoryService{
    Result addProductInventory(ProductInventory productInventory);

    <T> Result getAllProducts(Map<T,T> params);
}
