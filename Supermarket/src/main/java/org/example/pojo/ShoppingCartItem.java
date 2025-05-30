package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ShoppingCartItem {
    private Integer cartItemId;
    private String username;
    private String productName;
    private Integer unitPrice;
    private Integer num;
    private Integer totalPrice;
}
