package com.example.models.pivot;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pivots")
public class PivotTable {
  @Id
  private String id;
  private String name;
  private String tableId;
  private String userId;
  private PivotTableConfig config;

  public PivotTable() {

  }

  public PivotTable(String name, String tableId, String userId, PivotTableConfig config) {
    this.name = name;
    this.tableId = tableId;
    this.userId = userId;
    this.config = config;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTableId() {
    return tableId;
  }

  public void setTableId(String tableId) {
    this.tableId = tableId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public PivotTableConfig getConfig() {
    return config;
  }

  public void setConfig(PivotTableConfig config) {
    this.config = config;
  }
}
