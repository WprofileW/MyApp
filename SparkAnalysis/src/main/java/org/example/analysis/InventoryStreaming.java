package org.example.analysis;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.example.pojo.PurchaseRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InventoryStreaming {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf().setAppName("InventoryStreaming").setMaster("local[*]");
        // 每隔5秒钟接收一次数据
        JavaStreamingContext jssc = new JavaStreamingContext(conf, new Duration(5000));
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("group.id", "inventory_group");
        // Kafka topic name for purchase records
        Collection<String> topics = Arrays.asList("purchase_records", "stock_in");
        JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(
                jssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topics, kafkaParams)
        );
        // 处理消息
        JavaDStream<String> messages = stream.map(ConsumerRecord::value);
        // 处理消息逻辑
        messages.foreachRDD(rdd -> {
            rdd.foreachPartition(partitionOfRecords -> {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                String currentTime = sdf.format(Calendar.getInstance().getTime());
                String url = "jdbc:mysql://localhost:3306/supermarket";
                String user = "root";
                String password = "3033715900";
                Connection conn = DriverManager.getConnection(url, user, password);
                String insertSql = "INSERT INTO product_inventory_change (timestamp, product_name, inventory_change) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                partitionOfRecords.forEachRemaining(record -> {
                    // 解析JSON数据
                    Gson gson = new Gson();
                    PurchaseRecord purchaseRecord = gson.fromJson(record, PurchaseRecord.class);
                    // 设置购买操作和入库操作的数量为正负数
                    int inventoryChange = purchaseRecord.getAction().equals("purchase") ? -purchaseRecord.getQuantity() : purchaseRecord.getQuantity();
                    // 插入库存变化记录
                    try {
                        insertStmt.setString(1, currentTime);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        insertStmt.setString(2, purchaseRecord.getProductName());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        insertStmt.setInt(3, inventoryChange);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        insertStmt.addBatch();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                // 执行插入操作
                insertStmt.executeBatch();
                // 关闭连接和语句
                insertStmt.close();
                conn.close();
            });
        });
        jssc.start();
        jssc.awaitTermination();
    }
}