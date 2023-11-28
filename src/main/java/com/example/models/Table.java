package com.example.models;

import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tables")
public class Table {
  @Id
  private String id;
  private String userId;

  private String name;
  private String datasetId;
  private List<String> selectedColumns;
  private List<String> selectedCountries;
  private List<Map<String, Object>> data;

  public Table() {

  }

  public Table(String userId, String name, String datasetId,
      List<String> selectedColumns, List<String> selectedCountries,
      List<Map<String, Object>> data) {
    this.userId = userId;
    this.name = name;
    this.datasetId = datasetId;
    this.selectedColumns = selectedColumns;
    this.selectedCountries = selectedCountries;
    this.data = data;
  }

  public List<String> getSelectedCountries() {
    return selectedCountries;
  }

  public void setSelectedCountries(List<String> selectedCountries) {
    this.selectedCountries = selectedCountries;
  }

  public List<Map<String, Object>> getData() {
    return data;
  }

  public void setData(List<Map<String, Object>> data) {
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDatasetId() {
    return datasetId;
  }

  public void setDatasetId(String datasetId) {
    this.datasetId = datasetId;
  }

  public List<String> getSelectedColumns() {
    return selectedColumns;
  }

  public void setSelectedColumns(List<String> selectedColumns) {
    this.selectedColumns = selectedColumns;
  }
}
