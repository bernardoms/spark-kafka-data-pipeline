package com.bernardoms.miniclip;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.unix_timestamp;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.bernardoms.miniclip.config.HackedMongoConnector;
import com.bernardoms.miniclip.model.InitEvent;
import com.mongodb.MongoClient;
import com.mongodb.spark.MongoConnector;
import com.mongodb.spark.MongoSpark;
import com.mongodb.spark.config.ReadConfig;
import com.mongodb.spark.config.WriteConfig;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.bson.codecs.pojo.PojoCodecProvider;

public class SparkAggregator {
  public static void main(final String[] args) {

    Map<String, String> readOverrides = new HashMap<>();

    readOverrides
        .put("uri", "mongodb://local:local@mongo:27017/local.init-event?authSource=admin&authMechanism=SCRAM-SHA-1");

    ReadConfig readConfig = ReadConfig.create(readOverrides);

    MongoConnector mongoConnector = new HackedMongoConnector(readConfig,
        fromRegistries(MongoClient.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().register(InitEvent.class).build())));

    SparkSession spark = SparkSession.builder()
        .master("local")
        .appName("MongoSparkConnectorIntro")
        .config("spark.mongodb.input.uri",
            "mongodb://local:local@mongo:27017/local.init-event?authSource=admin&authMechanism=SCRAM-SHA-1")
        .config("spark.mongodb.output.uri",
            "mongodb://local:local@mongo:27017/local.aggregator?authSource=admin&authMechanism=SCRAM-SHA-1")
        .getOrCreate();

    JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

    MongoSpark mongoSpark = MongoSpark.builder()
        .javaSparkContext(jsc)
        .connector(mongoConnector)
        .readConfig(readConfig)
        .build();

    Map<String, String> writeOverrides = new HashMap<>();
    writeOverrides.put("collection", "aggregations");
    writeOverrides.put("writeConcern.w", "majority");

    WriteConfig writeConfig = WriteConfig.create(jsc).withOptions(writeOverrides);

    Dataset<InitEvent> df = mongoSpark.toDS(InitEvent.class);

    Dataset<Row> dailyTimeByCountryPlatform = df.where(
        unix_timestamp(col("time"), "yyyy-MM-dd")
            .cast("timestamp")
            .between(
                Timestamp
                    .valueOf(args.length > 0 ? LocalDateTime.parse(args[0]) : LocalDateTime.now().minusDays(1)),
                Timestamp.valueOf(args.length > 0 ? LocalDateTime.parse(args[0]) : LocalDateTime.now()))).dropDuplicates("userId").groupBy("country", "platform", "time")
        .count();

    MongoSpark.save(dailyTimeByCountryPlatform, writeConfig);
    jsc.close();
  }
}
