package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.TopProduct;

import java.util.List;
@Mapper
public interface TopProductMapper {
    @Select("""
            SELECT tp.timestamp, tp.product_name, tp.total_sales
            FROM top_products tp
            JOIN (
                SELECT DISTINCT timestamp
                FROM top_products
                ORDER BY timestamp DESC
                LIMIT 4
            ) AS latest_timestamps
            ON tp.timestamp = latest_timestamps.timestamp
            ORDER BY tp.timestamp DESC, tp.product_name;
            """)
    List<TopProduct> findLatestFourTopSale();

    @Insert("INSERT INTO top_products (timestamp, product_name, total_sales) VALUES (#{timestamp}, #{productName}, #{totalSales})")
    void insertTopProduct(TopProduct topProduct);

    @Delete("DELETE FROM top_products WHERE timestamp = #{timestamp}")
    void deleteTopProductByTimestamp(Long timestamp);

    @Update("UPDATE top_products SET product_name = #{productName}, total_sales = #{totalSales} WHERE timestamp = #{timestamp}")
    void updateTopProduct(TopProduct topProduct);

    @Select("SELECT * FROM top_products")
    List<TopProduct> getAllTopProducts();

    @Select("SELECT * FROM top_products WHERE timestamp = #{timestamp}")
    TopProduct getTopProductByTimestamp(Long timestamp);
}
