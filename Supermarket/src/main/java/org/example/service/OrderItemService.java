package org.example.service;

import org.example.pojo.OrderItem;
import org.example.pojo.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderItemService {

    Result addOrderItem(List<OrderItem> orderItemList);

    Result updateOrderItem(OrderItem orderItem);

    Result deleteOrderItem(OrderItem orderItem);

    <T> Result getAllOrders(Map<T, T> params);
}
