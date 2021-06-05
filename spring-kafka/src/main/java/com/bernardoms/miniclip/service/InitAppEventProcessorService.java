package com.bernardoms.miniclip.service;

import com.bernardoms.miniclip.model.InitEvent;
import com.bernardoms.miniclip.model.InitEventDTO;
import com.bernardoms.miniclip.repository.InitEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class InitAppEventProcessorService implements EventProcessor {
  private final InitEventRepository initEventRepository;

  private final ObjectMapper objectMapper;

  public InitAppEventProcessorService(InitEventRepository initEventRepository,
      ObjectMapper objectMapper) {
    this.initEventRepository = initEventRepository;
    this.objectMapper = objectMapper;
  }

  @Override
  public String type() {
    return "init";
  }

  @Override
  public void processEvent(String event) throws JsonProcessingException {
    InitEventDTO initEventDTO = objectMapper.readValue(event, InitEventDTO.class);

    InitEvent initEvent = new InitEvent();
    initEvent.setTime(initEventDTO.getTime());
    initEvent.setCountry(initEventDTO.getCountry());
    initEvent.setEventType(initEventDTO.getEventType());
    initEvent.setPlatform(initEventDTO.getPlatform());
    initEvent.setUserId(initEventDTO.getUserId());

    initEventRepository.save(initEvent);
  }
}
