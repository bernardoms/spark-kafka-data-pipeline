package com.bernardoms.miniclip.consumer;

import com.bernardoms.miniclip.service.EventProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Set;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);

  private Set<EventProcessor> eventProcessors;

  public KafkaConsumerService(Set<EventProcessor> eventProcessors) {
    this.eventProcessors = eventProcessors;
  }

  @KafkaListener(topics = "${message.topic.name}")
  public void listen(ConsumerRecord<?, ?> consumerRecord) throws JsonProcessingException {
    EventProcessor eventProcessor = eventProcessors.stream().filter(e -> e.type().equals(consumerRecord.key()))
        .findFirst().get();
    eventProcessor.processEvent(consumerRecord.value().toString());
    LOGGER.info("received payload='{}'", consumerRecord.value());
  }
}
