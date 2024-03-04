package org.example.producer;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import static java.lang.Thread.sleep;

public class KafkaProducerExample {
    public static void main(String[] args) throws InterruptedException {
        String bootstrapServers = "localhost:9092";
        List<String> topics = Arrays.asList("purchase_records", "stock_in");
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);
        Random random = new Random();
        // 随机发送购买操作或入库操作
        for (int i = 0; i < 8000; i++) {
            boolean isPurchase = random.nextBoolean(); // 随机生成购买操作或入库操作
            String topic = isPurchase ? topics.get(0) : topics.get(1);
            PurchaseRecord record = isPurchase ? generatePurchaseRecord() : generateStockInRecord();
            String jsonRecord = new Gson().toJson(record);
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, jsonRecord);
            producer.send(producerRecord);
            System.out.println("Sent record to topic " + topic + ": " + jsonRecord);
            sleep(1000);
        }
        producer.flush();
        producer.close();
    }

    static class PurchaseRecord {
        String action;
        String productName;
        int quantity;

        public PurchaseRecord(String action, String productName, int quantity) {
            this.action = action;
            this.productName = productName;
            this.quantity = quantity;
        }
    }

    private static PurchaseRecord generatePurchaseRecord() {
        List<String> productNames = Arrays.asList("电脑", "手机", "平板", "耳机", "苹果", "香蕉", "洗发水", "书籍", "手表", "电视", "相机", "衣服", "鞋子", "笔记本", "水杯", "墨镜");
        Random random = new Random();
        String productName = productNames.get(random.nextInt(productNames.size()));
        int quantity = random.nextInt(10) + 1;
        return new PurchaseRecord("purchase", productName, quantity);
    }

    private static PurchaseRecord generateStockInRecord() {
        List<String> productNames = Arrays.asList("电脑", "手机", "平板", "耳机", "苹果", "香蕉", "洗发水", "书籍", "手表", "电视", "相机", "衣服", "鞋子", "笔记本", "水杯", "墨镜");
        Random random = new Random();
        String productName = productNames.get(random.nextInt(productNames.size()));
        int quantity = random.nextInt(50) + 1;
        return new PurchaseRecord("stock_in", productName, quantity);
    }
}