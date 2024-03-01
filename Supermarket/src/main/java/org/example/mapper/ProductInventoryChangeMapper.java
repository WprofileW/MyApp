package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.ProductInventoryChange;
import java.util.List;

@Mapper
public interface ProductInventoryChangeMapper {
    @Select("""
            SELECT pic.timestamp, pic.product_name, pic.inventory_change
            FROM product_inventory_change pic
            JOIN (
                SELECT DISTINCT timestamp
                FROM product_inventory_change
                ORDER BY timestamp DESC
                LIMIT 5
            ) AS latest_timestamps
            ON pic.timestamp = latest_timestamps.timestamp
            ORDER BY pic.timestamp DESC, pic.product_name;
            """)
    List<ProductInventoryChange> findLatestFiveChanges();

    @Select("SELECT * FROM product_inventory_change WHERE timestamp = #{timestamp}")
    List<ProductInventoryChange> findByTimestamp(@Param("timestamp") long timestamp);

    @Insert("INSERT INTO product_inventory_change (timestamp, product_name, inventory_change) " +
            "VALUES (#{timestamp}, #{productName}, #{inventoryChange})")
    void insert(ProductInventoryChange productInventoryChange);

    @Update("UPDATE product_inventory_change SET product_name = #{productName}, " +
            "inventory_change = #{inventoryChange} WHERE timestamp = #{timestamp}")
    void update(ProductInventoryChange productInventoryChange);

    @Delete("DELETE FROM product_inventory_change WHERE timestamp = #{timestamp}")
    void deleteByTimestamp(@Param("timestamp") long timestamp);
}