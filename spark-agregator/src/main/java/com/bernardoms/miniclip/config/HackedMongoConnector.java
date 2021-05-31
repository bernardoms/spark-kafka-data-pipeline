package com.bernardoms.miniclip.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.spark.MongoClientFactory;
import com.mongodb.spark.MongoConnector;
import com.mongodb.spark.config.MongoSharedConfig;
import com.mongodb.spark.config.ReadConfig;
import java.io.Serializable;
import org.bson.codecs.configuration.CodecRegistry;

public class HackedMongoConnector extends MongoConnector implements Serializable {
  //this is custom CodecRegistry which Spark will use in deserialization
  //transient is important here because Spark loves to serialize anything
  private transient CodecRegistry codecRegistry;

  public HackedMongoConnector(ReadConfig readConfig, CodecRegistry codecRegistry) {
    super(new HackedMongoClientFactory(
        ReadConfig.stripPrefix(readConfig.asOptions()).get(MongoSharedConfig.mongoURIProperty()).get(),
        Integer.parseInt(ReadConfig.stripPrefix(readConfig.asOptions()).get(ReadConfig.localThresholdProperty()).get()),
        codecRegistry
    ));
    this.codecRegistry = codecRegistry;
  }

  @Override
  public CodecRegistry codecRegistry() {
    return codecRegistry;
  }
  //Sources of DefaultMongoClientFactory used and modified here
  private static class HackedMongoClientFactory implements MongoClientFactory {

    private String mongoURI;
    private int localThreshold;
    private transient CodecRegistry codecRegistry;

    public HackedMongoClientFactory(String mongoURI, int localThreshold, CodecRegistry codecRegistry) {
      this.mongoURI = mongoURI;
      this.localThreshold = localThreshold;
      this.codecRegistry = codecRegistry;
    }

    @Override
    public MongoClient create() {
      MongoClientOptions.Builder builder = MongoClientOptions.builder()
          .localThreshold(localThreshold)
          .codecRegistry(codecRegistry);
      MongoClient mongoClient = new HackedMongoClient(new MongoClientURI(mongoURI, builder), codecRegistry);
      return mongoClient;
    }

    private static class HackedMongoClient extends MongoClient {

      private transient CodecRegistry codecRegistry;

      public HackedMongoClient(MongoClientURI uri, CodecRegistry codecRegistry) {
        super(uri);
        this.codecRegistry = codecRegistry;
      }

      //this is the main point: when creating MongoDatabaseImpl, we provide codec immediately
      @Override
      public MongoDatabase getDatabase(String databaseName) {
        return super.getDatabase(databaseName).withCodecRegistry(codecRegistry);
      }
    }
  }
}