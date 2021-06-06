package com.bernardoms.miniclip.model;

public class Aggregation {
  private String aggregationKey;
  private Double value;

  public Aggregation(String aggregationKey, Double value) {
    this.aggregationKey = aggregationKey;
    this.value = value;
  }

  public String getAggregationKey() {
    return aggregationKey;
  }

  public Double getValue() {
    return value;
  }
}
