package com.bernardoms.miniclip.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
  @JsonProperty("coin-balance-after-match")
  private int coinBalanceAfterMatch;
  @JsonProperty("level-after-match")
  private int levelAfterMatch;
  private String device;
  private String platform;

  public int getCoinBalanceAfterMatch() {
    return coinBalanceAfterMatch;
  }

  public void setCoinBalanceAfterMatch(int coinBalanceAfterMatch) {
    this.coinBalanceAfterMatch = coinBalanceAfterMatch;
  }

  public int getLevelAfterMatch() {
    return levelAfterMatch;
  }

  public void setLevelAfterMatch(int levelAfterMatch) {
    this.levelAfterMatch = levelAfterMatch;
  }

  public String getDevice() {
    return device;
  }

  public void setDevice(String device) {
    this.device = device;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }
}
