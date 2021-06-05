package com.bernardoms.miniclip.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "init-event")
public class InitEvent {
  private String eventType;
  private LocalDate time;
  private String userId;
  private String country;
  private String platform;

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public LocalDate getTime() {
    return time;
  }

  public void setTime(LocalDate time) {
    this.time = time;
  }

  public void setTime(long time) {
    this.time = Instant.ofEpochSecond(time).atZone(ZoneId.systemDefault()).toLocalDate();
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
