package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.ShoppingCartItem;

import java.util.List;

@Mapper
public interface ShoppingCartItemMapper {

    @Select("SELECT * FROM shopping_cart_item WHERE cart_item_id = #{cartItemId}")
    ShoppingCartItem getShoppingCartItemById(@Param("cartItemId") Integer cartItemId);

    @Select("SELECT * FROM shopping_cart_item WHERE username = #{username}")
    List<ShoppingCartItem> getShoppingCartItemsByUsername(@Param("username") String username);

    @Select("SELECT * FROM shopping_cart_item")
    List<ShoppingCartItem> getAllShoppingCartItems();

    @Insert("INSERT INTO shopping_cart_item (username, product_name, unit_price, quantity, total_price) " +
            "VALUES (#{username}, #{productName}, #{unitPrice}, #{quantity}, #{totalPrice})")
    @Options(useGeneratedKeys = true, keyProperty = "cartItemId")
    void insertShoppingCartItem(ShoppingCartItem shoppingCartItem);

    @Update("UPDATE shopping_cart_item SET username = #{username}, product_name = #{productName}, " +
            "unit_price = #{unitPrice}, quantity = #{quantity}, total_price = #{totalPrice} " +
            "WHERE cart_item_id = #{cartItemId}")
    void updateShoppingCartItem(ShoppingCartItem shoppingCartItem);

    @Delete("DELETE FROM shopping_cart_item WHERE cart_item_id = #{cartItemId}")
    void deleteShoppingCartItemById(@Param("cartItemId") Integer cartItemId);
}

