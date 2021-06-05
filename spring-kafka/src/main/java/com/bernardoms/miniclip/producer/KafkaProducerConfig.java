package com.bernardoms.miniclip.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {

  @Value(value = "${kafka.producerTopic}")
  private String producerTopic;

  public String getProducerTopic() {
    return producerTopic;
  }
}
