package com.bernardoms.miniclip.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchEventDTO {
  @JsonProperty("event-type")
  private String eventType;
  private int time;
  @JsonProperty("user-a")
  private String userA;
  @JsonProperty("user-b")
  private String userB;
  @JsonProperty("user-a-postmatch-info")
  private User UserAPostmanInfo;
  @JsonProperty("user-b-postmatch-info")
  private User UserBPostmanInfo;
  private String winner;
  @JsonProperty("game-tie")
  private String gameTie;
  private int duration;

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public int getTime() {
    return time;
  }

  public void setTime(int time) {
    this.time = time;
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
