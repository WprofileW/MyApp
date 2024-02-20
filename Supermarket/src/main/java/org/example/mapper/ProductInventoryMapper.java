package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.ProductInventory;

import java.util.List;

@Mapper
public interface ProductInventoryMapper {

    @Select("SELECT * FROM product_inventory WHERE inventory_id = #{inventoryId}")
    ProductInventory getProductInventoryById(@Param("inventoryId") Integer inventoryId);

    @Select("SELECT * FROM product_inventory WHERE product_name = #{productName}")
    ProductInventory getProductInventoryByName(@Param("productName") String productName);

    @Select("SELECT * FROM product_inventory")
    List<ProductInventory> getAllProductInventories();

    @Insert("INSERT INTO product_inventory (product_name, unit_price, quantity, category, supplier, warehouse_name, update_time) " +
            "VALUES (#{productName}, #{unitPrice}, #{quantity}, #{category}, #{supplier}, #{warehouseName},NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "inventoryId")
    void insertProductInventory(ProductInventory productInventory);

    @Update("UPDATE product_inventory SET product_name=#{productName}, unit_price = #{unitPrice}, quantity = #{quantity}, " +
            "category = #{category}, supplier = #{supplier}, warehouse_name = #{warehouseName}, update_time = NOW() " +
            "WHERE inventory_id = #{inventoryId}")
    void updateProductInventory(ProductInventory productInventory);

    @Delete("DELETE FROM product_inventory WHERE inventory_id = #{inventoryId}")
    void deleteProductInventoryById(@Param("inventoryId") Integer inventoryId);
}
