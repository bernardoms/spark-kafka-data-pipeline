package com.bernardoms.miniclip.service;

import com.bernardoms.miniclip.model.InAppPurchaseEvent;
import com.bernardoms.miniclip.model.InAppPurchaseEventDTO;
import com.bernardoms.miniclip.repository.InAppPurchaseEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class InAppPurchaseEventProcessorService implements EventProcessor {

  private final InAppPurchaseEventRepository inAppPurchaseEventRepository;

  private final ObjectMapper objectMapper;

  public InAppPurchaseEventProcessorService(
      InAppPurchaseEventRepository inAppPurchaseEventRepository,
      ObjectMapper objectMapper) {
    this.inAppPurchaseEventRepository = inAppPurchaseEventRepository;
    this.objectMapper = objectMapper;
  }

  @Override
  public String type() {
    return "in-app-purchase";
  }

  @Override
  public void processEvent(String event) throws JsonProcessingException {
    InAppPurchaseEventDTO inAppPurchaseEventDTO = objectMapper.readValue(event, InAppPurchaseEventDTO.class);

    InAppPurchaseEvent inAppPurchaseEvent = new InAppPurchaseEvent();
    inAppPurchaseEvent.setEventType(inAppPurchaseEventDTO.getEventType());
    inAppPurchaseEvent.setProductId(inAppPurchaseEventDTO.getProductId());
    inAppPurchaseEvent.setPurchaseValue(inAppPurchaseEventDTO.getPurchaseValue());
    inAppPurchaseEvent.setTime(inAppPurchaseEventDTO.getTime());
    inAppPurchaseEvent.setUserId(inAppPurchaseEventDTO.getUserId());

    inAppPurchaseEventRepository.save(inAppPurchaseEvent);
  }
}
