package com.bezkoder.spring.jwt.mongodb.models;

import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "charts")
public class Chart {

  @Id
  private String id;
  private String userId;
  private String pivotId;
  private String name;

  private String type;
  private String renderAt;
  private String width;
  private String height;

  private ChartConfig chartConfig;

  // data can be passed directly from the table in case we are not going to use the pivot.
  private List<Map<String, Object>> data;

  public Chart() {

  }

  public Chart(String userId, String pivotId, String name, String type, String renderAt,
      String width, String height, ChartConfig chartConfig,
      List<Map<String, Object>> data) {
    this.userId = userId;
    this.pivotId = pivotId;
    this.name = name;
    this.type = type;
    this.renderAt = renderAt;
    this.width = width;
    this.height = height;
    this.chartConfig = chartConfig;
    this.data = data;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPivotId() {
    return pivotId;
  }

  public void setPivotId(String tableId) {
    this.pivotId = tableId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getRenderAt() {
    return renderAt;
  }

  public void setRenderAt(String renderAt) {
    this.renderAt = renderAt;
  }

  public String getWidth() {
    return width;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public ChartConfig getChartConfig() {
    return chartConfig;
  }

  public void setChartConfig(ChartConfig chartConfig) {
    this.chartConfig = chartConfig;
  }

  public List<Map<String, Object>> getData() {
    return data;
  }

  public void setData(List<Map<String, Object>> data) {
    this.data = data;
  }
}
