package com.bernardoms.miniclip.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class InitEventKafka implements Serializable {
  @JsonProperty("event-type")
  private String eventType;
  private long time;
  @JsonProperty("user-id")
  private String userId;
  private String country;
  private String platform;

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }
}
