package com.bernardoms.miniclip;

import com.bernardoms.miniclip.model.Aggregation;
import com.bernardoms.miniclip.model.InAppPurchaseEventKafka;
import com.bernardoms.miniclip.model.InitEventKafka;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.spark.MongoSpark;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

public class SparkStreamingAggregator {
  public static void main(final String[] args) throws InterruptedException {

    SparkConf sparkConf = new SparkConf().setAppName("kafkaSparkStream")
        .setMaster("local[*]").set("spark.mongodb.output.uri",
            "mongodb://local:local@mongo:27017/local.aggregations?authSource=admin&authMechanism=SCRAM-SHA-1");

    JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, new Duration(1000));

    Map<String, Object> kafkaParams = new HashMap<>();
    kafkaParams.put("bootstrap.servers", "kafka:9092");
    kafkaParams.put("key.deserializer", StringDeserializer.class);
    kafkaParams.put("value.deserializer", StringDeserializer.class);
    kafkaParams.put("group.id", "0");
    // Automatically reset the offset to the earliest offset
    kafkaParams.put("auto.offset.reset", "earliest");
    kafkaParams.put("enable.auto.commit", false);

    Collection<String> topicName = Collections.singleton("ballpoolevent");

    JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils
        .createDirectStream(ssc, LocationStrategies.PreferConsistent(),
            ConsumerStrategies.Subscribe(topicName, kafkaParams));

    JavaDStream<String> initEvent = stream.filter(c -> c.key().equals("init")).map(ConsumerRecord::value);
    JavaDStream<String> inAppPurchaseEvent = stream.filter(c -> c.key().equals("in-app-purchase")).map(
        ConsumerRecord::value);

    JavaDStream<InitEventKafka> initEventKafkaJavaDStream = initEvent.map(y -> {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(y, InitEventKafka.class);
    });

    JavaDStream<InAppPurchaseEventKafka> inAppPurchaseEventKafkaJavaDStream = inAppPurchaseEvent.map(y -> {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(y, InAppPurchaseEventKafka.class);
    });

    inAppPurchaseEventKafkaJavaDStream.foreachRDD(rdd -> {
      Aggregation aggregation = new Aggregation("purchase-sum", rdd.mapToDouble(
          InAppPurchaseEventKafka::getPurchaseValue).sum());

      SQLContext orCreate = SQLContext.getOrCreate(rdd.context());
      Dataset<Row> dataFrame = orCreate.createDataFrame(Collections.singletonList(aggregation), Aggregation.class);
      MongoSpark.save(dataFrame);
    });

    initEventKafkaJavaDStream.foreachRDD(rdd -> {

      long distinctUsers = rdd.keyBy(InitEventKafka::getUserId).reduceByKey((InitEventKafka v1, InitEventKafka v2) -> v1).values()
          .count();

      Aggregation aggregation = new Aggregation("distinct-users", (double) distinctUsers);

      SQLContext orCreate = SQLContext.getOrCreate(rdd.context());
      Dataset<Row> dataFrame = orCreate.createDataFrame(Collections.singletonList(aggregation), Aggregation.class);
      MongoSpark.save(dataFrame);

      long distinctCounty = rdd.keyBy(InitEventKafka::getCountry).reduceByKey((InitEventKafka v1, InitEventKafka v2) -> v1).values()
          .count();

      aggregation = new Aggregation("distinct-country", (double) distinctCounty);

      dataFrame = orCreate.createDataFrame(Collections.singletonList(aggregation), Aggregation.class);
      MongoSpark.save(dataFrame);
    });

    ssc.start();
    ssc.awaitTermination();
  }
}
