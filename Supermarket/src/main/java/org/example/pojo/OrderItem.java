package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderItem {
    private Integer orderItemId;
    private String username;
    private String productName;
    private Integer unitPrice;
    private Integer quantity;
    private Integer totalPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
}

