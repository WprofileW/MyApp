package org.example.analysis;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.ForeachWriter;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.StructType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import static org.apache.spark.sql.functions.*;

public class Top3SalesPerMinute {
    public static void main(String[] args) throws StreamingQueryException, TimeoutException {
        // 创建SparkSession
        SparkSession spark = SparkSession.builder()
                .appName("Top3SalesPerMinuteToDatabase")
                .master("local[*]")
                .getOrCreate();
        // 从Kafka读取数据
        Dataset<Row> kafkaDF = spark
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "purchase_records")
                .option("startingOffsets", "latest")
                .load();

        // 定义数据结构
        StructType schema = new StructType()
                .add("action", "string")
                .add("productName", "string")
                .add("quantity", "integer");

        // 解析JSON数据
        Dataset<Row> parsedDF = kafkaDF.selectExpr("CAST(value AS STRING)")
                .select(from_json(col("value"), schema).as("data"))
                .select("data.*");

        // 添加当前时间戳列
        Dataset<Row> withTimestampDF = parsedDF.withColumn("eventTime", current_timestamp());

        // 获取当前时间戳的时间戳表示
        Dataset<Row> currentTimestampDF = withTimestampDF.withColumn("current_timestamp", current_timestamp());

        // 计算一分钟前的时间戳
        Dataset<Row> oneMinuteAgoTimestampDF = currentTimestampDF.withColumn("one_minute_ago_timestamp", expr("to_timestamp(current_timestamp) - interval 1 minute"));

        // 过滤出 "action" 列值为 "purchase" 的记录，并且时间戳在当前时间的前一分钟内
        Dataset<Row> lastMinuteData = oneMinuteAgoTimestampDF
                .filter(col("action").equalTo("purchase"))
                .filter(col("eventTime").gt(col("one_minute_ago_timestamp")));

        // 对上一分钟的数据按产品名称进行分组并计算总销量
        Dataset<Row> productSales = lastMinuteData
                .groupBy(col("productName"))
                .sum("quantity")
                .withColumnRenamed("sum(quantity)", "total_sales");

        // 对每分钟的销量数据按总销量降序排列，并取前三个产品
        Dataset<Row> top3ProductsPerMinute = productSales
                .orderBy(col("total_sales").desc())
                .limit(3);

//        // 启动流式查询以处理数据
//        StreamingQuery query = top3ProductsPerMinute
//                .writeStream()
//                .outputMode("complete")
//                .format("console")
//                .start();
//
//        query.awaitTermination();
        // 定义数据库连接属性
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", "root");
        connectionProperties.put("password", "3033715900");

        // 将数据写入数据库
        StreamingQuery query = top3ProductsPerMinute
                .writeStream()
                .outputMode("complete")
                .foreach(new ForeachWriter<Row>() {
                    @Override
                    public boolean open(long partitionId, long version) {
                        // 在此处打开数据库连接
                        return true; // 返回 true 表示成功打开连接
                    }

                    @Override
                    public void process(Row row) {
                        // 在此处将每一行数据写入数据库
                        String productName = row.getString(0);
                        long totalSales = row.getLong(1);

                        // 将数据插入MySQL数据库
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
                            String currentTime = sdf.format(Calendar.getInstance().getTime());
                            String url = "jdbc:mysql://localhost:3306/supermarket";
                            Connection conn = DriverManager.getConnection(url, connectionProperties);
                            PreparedStatement statement = conn.prepareStatement("INSERT INTO top_products (timestamp,product_name, total_sales) VALUES (?,?, ?)");
                            statement.setString(1, currentTime);
                            statement.setString(2, productName);
                            statement.setLong(3, totalSales);
                            statement.executeUpdate();
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void close(Throwable errorOrNull) {
                        // 在此处关闭数据库连接
                    }
                })
                .start();

        query.awaitTermination();
    }
}


//import org.apache.spark.sql.Dataset;
//import org.apache.spark.sql.ForeachWriter;
//import org.apache.spark.sql.Row;
//import org.apache.spark.sql.SparkSession;
//import org.apache.spark.sql.streaming.StreamingQuery;
//import org.apache.spark.sql.streaming.StreamingQueryException;
//import org.apache.spark.sql.types.DataTypes;
//import org.apache.spark.sql.types.StructType;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.concurrent.TimeoutException;
//
//import static org.apache.spark.sql.functions.col;
//import static org.apache.spark.sql.functions.from_json;
//
//public class Top3Sales {
//    public static void main(String[] args) throws StreamingQueryException, TimeoutException {
//        // 创建SparkSession
//        SparkSession spark = SparkSession.builder()
//                .appName("Top3SalesFromKafka")
//                .master("local[*]")
//                .getOrCreate();
//
//        // 从Kafka读取数据
//        Dataset<Row> kafkaDF = spark
//                .readStream()
//                .format("kafka")
//                .option("kafka.bootstrap.servers", "localhost:9092")
//                .option("subscribe", "purchase_records")
//                .option("startingOffsets", "latest")
//                .load();
//
//        // 定义数据结构
//        StructType schema = new StructType()
//                .add("action", "string")
//                .add("productName", "string")
//                .add("quantity", "integer");
//
//        // 解析JSON数据
//        Dataset<Row> parsedDF = kafkaDF.selectExpr("CAST(value AS STRING)")
//                .select(from_json(col("value"), schema).as("data"))
//                .select("data.*");
//
//        // 过滤出购买行为
//        Dataset<Row> purchaseDF = parsedDF.filter(col("action").equalTo("purchase"));
//
//        // 计算每个产品的销售数量总和
//        Dataset<Row> productSales = purchaseDF
//                .groupBy("productName")
//                .sum("quantity")
//                .withColumnRenamed("sum(quantity)", "total_sales")
//                .withColumn("total_sales", col("total_sales").cast(DataTypes.IntegerType));
//
//        // 按销售数量降序排序，并取前三个产品
//        Dataset<Row> top3Products = productSales.orderBy(col("total_sales").desc()).limit(3);
//
//
//        // 写入数据库
//        StreamingQuery query = top3Products.writeStream()
//                .outputMode("complete")
//                .foreach(new ForeachWriter<Row>() {
//                    private Connection connection;
//                    private PreparedStatement preparedStatement;
//
//                    @Override
//                    public boolean open(long partitionId, long version) {
//                        try {
//                            // 连接数据库
//                            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarket", "root", "3033715900");
//                            return true;
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                            return false;
//                        }
//                    }
//
//                    @Override
//                    public void process(Row row) {
//                        try {
//                            // 插入数据到数据库
//                            preparedStatement = connection.prepareStatement("INSERT INTO top3_sales(productName, totalSales) VALUES (?, ?)");
//                            preparedStatement.setString(1, row.getString(0));
//                            preparedStatement.setInt(2, row.getInt(1));
//                            preparedStatement.executeUpdate();
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void close(Throwable errorOrNull) {
//                        try {
//                            // 关闭连接
//                            connection.close();
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                })
//                .start();
//
//        query.awaitTermination();
//
//        // 停止SparkSession
//        spark.stop();
//
//    }
//}
