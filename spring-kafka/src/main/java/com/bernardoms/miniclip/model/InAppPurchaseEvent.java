package com.bernardoms.miniclip.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "in-app-purchase-event")
public class InAppPurchaseEvent {
  private String eventType;
  private LocalDate time;
  private int purchaseValue;
  private String userId;
  private String productId;

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
