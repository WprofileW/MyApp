package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ProductInventory {
    private Integer inventoryId;
    private String productName;
    private Integer unitPrice;
    private Integer quantity;
    private String category;
    private String supplier;
    private String warehouseName;
    private LocalDateTime updateTime;
}


