package com.bernardoms.miniclip.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface EventProcessor {
  String type();
  void processEvent(String event) throws JsonProcessingException;
}
