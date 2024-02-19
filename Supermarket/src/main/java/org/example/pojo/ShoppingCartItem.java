package org.example.pojo;

import lombok.Data;

@Data
public class ShoppingCartItem {
    private Integer cartItemId;
    private String username;
    private String productName;
    private Integer unitPrice;
    private Integer quantity;
    private Integer totalPrice;
}
