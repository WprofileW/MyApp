package org.example.producer;

//import org.apache.kafka.clients.producer.*;
//import com.google.gson.Gson;
//
//import java.util.Properties;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
//public class KafkaProducerExample {
//
//    public static void main(String[] args) {
//        String bootstrapServers = "localhost:9092";
//        List<String> topics = Arrays.asList("purchase_records", "stock_in");
//
//        Properties props = new Properties();
//        props.put("bootstrap.servers", bootstrapServers);
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//
//        Producer<String, String> producer = new KafkaProducer<>(props);
//
//        // 模拟发送购买操作数据
//        for (int i = 0; i < 50; i++) {
//            PurchaseRecord purchaseRecord = new PurchaseRecord("purchase", getRandomProductName(), getRandomQuantity());
//            String jsonPurchase = new Gson().toJson(purchaseRecord);
//            ProducerRecord<String, String> record = new ProducerRecord<>(topics.get(0), jsonPurchase);
//            producer.send(record);
//            System.out.println("Sent stock in record: " + jsonPurchase);
//        }
//
//        // 模拟发送入库操作数据
//        for (int i = 0; i < 50; i++) {
//            PurchaseRecord stockInRecord = new PurchaseRecord("stock_in", getRandomProductName(), getRandomQuantity());
//            String jsonStockIn = new Gson().toJson(stockInRecord);
//            ProducerRecord<String, String> record = new ProducerRecord<>(topics.get(1), jsonStockIn);
//            producer.send(record);
//            System.out.println("Sent stock in record: " + jsonStockIn);
//        }
//
//        producer.flush();
//        producer.close();
//    }
//
//    static class PurchaseRecord {
//        String action;
//        String productName;
//        int quantity;
//
//        public PurchaseRecord(String action, String productName, int quantity) {
//            this.action = action;
//            this.productName = productName;
//            this.quantity = quantity;
//        }
//    }
//
//    private static String getRandomProductName() {
//        List<String> productNames = Arrays.asList("电脑", "手机", "平板", "耳机");
//        Random random = new Random();
//        return productNames.get(random.nextInt(productNames.size()));
//    }
//
//    private static int getRandomQuantity() {
//        Random random = new Random();
//        return random.nextInt(10) + 1; // 生成1到10之间的随机数作为数量
//    }
//}

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
        List<String> productNames = Arrays.asList("电脑", "手机", "平板", "耳机");
        Random random = new Random();
        String productName = productNames.get(random.nextInt(productNames.size()));
        int quantity = random.nextInt(10) + 1;
        return new PurchaseRecord("purchase", productName, quantity);
    }

    private static PurchaseRecord generateStockInRecord() {
        List<String> productNames = Arrays.asList("电脑", "手机", "平板", "耳机");
        Random random = new Random();
        String productName = productNames.get(random.nextInt(productNames.size()));
        int quantity = random.nextInt(50) + 1;
        return new PurchaseRecord("stock_in", productName, quantity);
    }
}
