package com.bezkoder.spring.jwt.mongodb.models;

import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "datasets")
public class Dataset {
  @Id
  private String id;

  private String name;
  private String databaseName;
  private List<Map<String, Object>> data;

  private List<String> measures;

  public Dataset() {

  }

  public Dataset(String name, String databaseName,
      List<Map<String, Object>> data, List<String> measures) {
    this.name = name;
    this.databaseName = databaseName;
    this.data = data;
    this.measures = measures;
  }

  public List<String> getMeasures() {
    return measures;
  }

  public void setMeasures(List<String> measures) {
    this.measures = measures;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  @Override
  public String toString() {
    return "Dataset{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", databaseName='" + databaseName + '\'' +
        '}';
  }
}
