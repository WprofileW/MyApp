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

    @Insert("INSERT INTO shopping_cart_item (cart_item_id,username, product_name, unit_price, num, total_price) " +
            "VALUES (#{cartItemId},#{username}, #{productName}, #{unitPrice}, #{num}, #{unitPrice}*#{num})")
    void insertShoppingCartItem(ShoppingCartItem shoppingCartItem);

    @Update("UPDATE shopping_cart_item SET username = #{username}, product_name = #{productName}, " +
            "unit_price = #{unitPrice}, num = #{num}, total_price = #{unitPrice}*#{num} " +
            "WHERE cart_item_id = #{cartItemId}")
    void updateShoppingCartItem(ShoppingCartItem shoppingCartItem);

    @Delete("DELETE FROM shopping_cart_item WHERE cart_item_id = #{cartItemId}")
    void deleteShoppingCartItemById(@Param("cartItemId") Integer cartItemId);

    @Delete("DELETE FROM shopping_cart_item WHERE username = #{username}")
    void deleteShoppingCartItemByUsername(String username);
}

