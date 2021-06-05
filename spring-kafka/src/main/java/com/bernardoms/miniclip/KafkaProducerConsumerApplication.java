package com.bernardoms.miniclip;

import com.bernardoms.miniclip.model.MatchEventDTO;
import com.bernardoms.miniclip.producer.KafkaProducerService;
import com.bernardoms.miniclip.model.InAppPurchaseEventDTO;
import com.bernardoms.miniclip.model.InitEventDTO;
import com.bernardoms.miniclip.repository.InAppPurchaseEventRepository;
import com.bernardoms.miniclip.repository.InitEventRepository;
import com.bernardoms.miniclip.repository.MatchEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.modelmapper.ModelMapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
@EnableMongoRepositories(basePackageClasses = { InAppPurchaseEventRepository.class, InitEventRepository.class,
    MatchEventRepository.class })
public class KafkaProducerConsumerApplication implements ApplicationRunner {

  private final KafkaProducerService kafkaProducerService;

  private final ResourceLoader resourceLoader;

  public KafkaProducerConsumerApplication(
      KafkaProducerService kafkaProducerService, ResourceLoader resourceLoader) {
    this.kafkaProducerService = kafkaProducerService;
    this.resourceLoader = resourceLoader;
  }

  public static void main(String[] args) {
    SpringApplication.run(KafkaProducerConsumerApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    Resource inAppPurchaseJsonResource = resourceLoader.getResource("classpath:/" + "in-app-purchase-event-1.json");
    Resource initEventJsonResource = resourceLoader.getResource("classpath:/" + "init-event-1.json");
    Resource matchEvent = resourceLoader.getResource("classpath:/match-event-1.json");

    InAppPurchaseEventDTO[] inAppPurchaseEventDTO = objectMapper
        .readValue(inAppPurchaseJsonResource.getInputStream(), InAppPurchaseEventDTO[].class);

    InitEventDTO[] initEventDTO = objectMapper.readValue(initEventJsonResource.getInputStream(), InitEventDTO[].class);

    MatchEventDTO[] matchEventDTO = objectMapper.readValue(matchEvent.getInputStream(), MatchEventDTO[].class);



    Arrays.stream(matchEventDTO).forEach(purchase -> {
      try {
        kafkaProducerService
            .send(objectMapper.writeValueAsString(purchase), purchase.getEventType());
      } catch (JsonProcessingException e) {
        System.out.println("Error processing the event " + e.getMessage());
      }
    });

    Arrays.stream(inAppPurchaseEventDTO).forEach(purchase -> {
      try {
        kafkaProducerService
      .send(objectMapper.writeValueAsString(purchase), purchase.getEventType());
      } catch (JsonProcessingException e) {
        System.out.println("Error processing the event " + e.getMessage());
      }
    });

    Arrays.stream(initEventDTO).forEach(init -> {
      try {
        kafkaProducerService
            .send(objectMapper.writeValueAsString(init), init.getEventType());
      } catch (JsonProcessingException e) {
        System.out.println("Error processing the event " + e.getMessage());
      }
    });
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public ObjectMapper getObjectMapper() {
    return new ObjectMapper();
  }
}
