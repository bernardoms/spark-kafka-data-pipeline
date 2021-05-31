package com.bernardoms.miniclip.service;

import com.bernardoms.miniclip.model.InAppPurchaseEvent;
import com.bernardoms.miniclip.model.InAppPurchaseEventDTO;
import com.bernardoms.miniclip.repository.InAppPurchaseEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class InAppPurchaseEventProcessorService implements EventProcessor<InAppPurchaseEventDTO> {

  private final InAppPurchaseEventRepository inAppPurchaseEventRepository;

  private final ModelMapper modelMapper;

  private final ObjectMapper objectMapper;

  public InAppPurchaseEventProcessorService(
      InAppPurchaseEventRepository inAppPurchaseEventRepository, ModelMapper modelMapper,
      ObjectMapper objectMapper) {
    this.inAppPurchaseEventRepository = inAppPurchaseEventRepository;
    this.modelMapper = modelMapper;
    this.objectMapper = objectMapper;
  }

  @Override
  public String type() {
    return "in-app-purchase";
  }

  @Override
  public void processEvent(String event) throws JsonProcessingException {
    inAppPurchaseEventRepository
        .save(modelMapper.map(objectMapper.readValue(event, InAppPurchaseEventDTO.class), InAppPurchaseEvent.class));
  }
}
