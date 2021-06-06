package com.bernardoms.miniclip.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class InAppPurchaseEventKafka implements Serializable {
  @JsonProperty("event-type")
  private String eventType;
  private int time;
  @JsonProperty("purchase_value")
  private int purchaseValue;
  @JsonProperty("user-id")
  private String userId;
  @JsonProperty("product-id")
  private String productId;

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

  public int getPurchaseValue() {
    return purchaseValue;
  }

  public void setPurchaseValue(int purchaseValue) {
    this.purchaseValue = purchaseValue;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }
}
