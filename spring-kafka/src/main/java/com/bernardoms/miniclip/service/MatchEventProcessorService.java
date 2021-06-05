package com.bernardoms.miniclip.service;

import com.bernardoms.miniclip.model.MatchEvent;
import com.bernardoms.miniclip.model.MatchEventDTO;
import com.bernardoms.miniclip.repository.MatchEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class MatchEventProcessorService implements EventProcessor {

  private final MatchEventRepository matchEventRepository;

  private final ObjectMapper objectMapper;

  public MatchEventProcessorService(MatchEventRepository matchEventRepository,
      ObjectMapper objectMapper) {
    this.matchEventRepository = matchEventRepository;
    this.objectMapper = objectMapper;
  }

  @Override
  public String type() {
    return "match";
  }

  @Override
  public void processEvent(String event) throws JsonProcessingException {
    MatchEventDTO matchEventDTO = objectMapper.readValue(event, MatchEventDTO.class);

    MatchEvent matchEvent = new MatchEvent();

    matchEvent.setDuration(matchEventDTO.getDuration());
    matchEvent.setEventType(matchEventDTO.getEventType());
    matchEvent.setGameTie(matchEventDTO.getEventType());
    matchEvent.setUserA(matchEventDTO.getUserA());
    matchEvent.setUserB(matchEventDTO.getUserB());
    matchEvent.setUserAPostmanInfo(matchEventDTO.getUserAPostmanInfo());
    matchEvent.setUserBPostmanInfo(matchEventDTO.getUserBPostmanInfo());
    matchEvent.setWinner(matchEventDTO.getWinner());
    matchEvent.setTime(matchEventDTO.getTime());

    matchEventRepository.save(matchEvent);
  }
}
