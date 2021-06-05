package com.bernardoms.miniclip.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "match-event")
public class MatchEvent {
  private String eventType;
  private LocalDate time;
  private String userA;
  private String userB;
  private User UserAPostmanInfo;
  private User UserBPostmanInfo;
  private String winner;
  private String gameTie;
  private int duration;

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

  public String getUserA() {
    return userA;
  }

  public void setUserA(String userA) {
    this.userA = userA;
  }

  public String getUserB() {
    return userB;
  }

  public void setUserB(String userB) {
    this.userB = userB;
  }

  public User getUserAPostmanInfo() {
    return UserAPostmanInfo;
  }

  public void setUserAPostmanInfo(User userAPostmanInfo) {
    UserAPostmanInfo = userAPostmanInfo;
  }

  public User getUserBPostmanInfo() {
    return UserBPostmanInfo;
  }

  public void setUserBPostmanInfo(User userBPostmanInfo) {
    UserBPostmanInfo = userBPostmanInfo;
  }

  public String getWinner() {
    return winner;
  }

  public void setWinner(String winner) {
    this.winner = winner;
  }

  public String getGameTie() {
    return gameTie;
  }

  public void setGameTie(String gameTie) {
    this.gameTie = gameTie;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }
}
