package com.bernardoms.miniclip.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final KafkaProducerConfig kafkaProducerConfig;

  public KafkaProducerService(
      KafkaTemplate<String, String> kafkaTemplate,
      KafkaProducerConfig kafkaProducerConfig) {
    this.kafkaTemplate = kafkaTemplate;
    this.kafkaProducerConfig = kafkaProducerConfig;
  }

  public void send(Object payload, String eventType) {
    Message<Object> message = MessageBuilder.withPayload(payload)
        .setHeader(KafkaHeaders.MESSAGE_KEY, eventType)
        .setHeader(KafkaHeaders.TOPIC, kafkaProducerConfig.getProducerTopic())
        .build();

    LOGGER.info("sending payload='{}' to topic='{}'", payload, kafkaProducerConfig.getProducerTopic());
    kafkaTemplate.send(message);
  }
}
