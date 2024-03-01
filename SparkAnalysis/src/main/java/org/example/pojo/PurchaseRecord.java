package org.example.pojo;

import lombok.Data;

@Data
public class PurchaseRecord {
    private String action;
    private String productName;
    private Integer quantity;
}