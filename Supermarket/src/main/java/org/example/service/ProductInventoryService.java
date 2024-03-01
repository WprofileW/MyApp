package org.example.service;

import org.example.pojo.ProductInventory;
import org.example.pojo.Result;

import java.util.Map;

public interface ProductInventoryService {
    Result addProduct(ProductInventory productInventory);

    Result updateProduct(ProductInventory productInventory);

    Result updateProductByName(ProductInventory productInventory);

    <T> Result getAllProducts(Map<T, T> params);

    Result deleteProduct(ProductInventory productInventory);

    <T> Result getProductByName(Map<T, T> params);
}
