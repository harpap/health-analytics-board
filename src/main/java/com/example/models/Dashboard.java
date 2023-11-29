package com.example.models;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dashboards")
public class Dashboard {
  @Id
  private String id;

  private String userId;
  private String name;
  private List<String> chartIds;

  public Dashboard() {

  }

  public Dashboard(String userId, String name, List<String> chartIds) {
    this.userId = userId;
    this.name = name;
    this.chartIds = chartIds;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getChartIds() {
    return chartIds;
  }

  public void setChartIds(List<String> chartIds) {
    this.chartIds = chartIds;
  }
}
