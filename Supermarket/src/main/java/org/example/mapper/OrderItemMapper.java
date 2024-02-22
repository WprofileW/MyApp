package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.OrderItem;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    @Select("SELECT * FROM order_item WHERE order_item_id = #{orderItemId}")
    OrderItem getOrderItemById(@Param("orderItemId") Integer orderItemId);

    @Select("SELECT * FROM order_item WHERE username = #{username}")
    List<OrderItem> getOrderItemsByUsername(String username);

    @Select("SELECT * FROM order_item")
    List<OrderItem> getAllOrderItems();

    @Insert("INSERT INTO order_item (username, product_name, unit_price, quantity, total_price, order_date) " +
            "VALUES (#{username}, #{productName}, #{unitPrice}, #{quantity}, #{totalPrice}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "orderItemId")
    void insertOrderItem(OrderItem orderItem);

    @Update("UPDATE order_item SET username = #{username}, product_name = #{productName}, " +
            "unit_price = #{unitPrice}, quantity = #{quantity}, total_price = #{totalPrice}, order_date = #{orderDate} " +
            "WHERE order_item_id = #{orderItemId}")
    void updateOrderItem(OrderItem orderItem);

    @Delete("DELETE FROM order_item WHERE order_item_id = #{orderItemId}")
    void deleteOrderItemById(@Param("orderItemId") Integer orderItemId);
}
