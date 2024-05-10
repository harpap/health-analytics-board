package com.example.models;

import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Document(collection = "datasets")
public class Dataset {
  private static final Logger logger = LoggerFactory.getLogger(Dataset.class);
  @Id
  private String id;
  private String name;
  private String category;
  private String databaseName;
  private String datasetName;
  private List<Map<String, Object>> data;

  private List<String> elements;

  public Dataset() {

  }

  public Dataset(String name, String category, String databaseName, String datasetName,
      List<Map<String, Object>> data, List<String> elements) {
    this.name = datasetName;
    this.category = category;
    this.databaseName = databaseName;
    this.datasetName = datasetName;
    this.data = data;
    this.elements = elements;
  }

  public List<String> getElements() {
    return elements;
  }

  public void setElements(List<String> elements) {
    this.elements = elements;
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

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  public String getDatasetName() {
    System.out.println("this.datasetName");
    logger.info(this.datasetName);
    return datasetName;
  }

  public void setDatasetName(String datasetName) {
    this.datasetName = datasetName;
  }

  @Override
  public String toString() {
    return "Dataset{" +
        "id='" + id + '\'' +
        ", category='" + category + '\'' +
        ", databaseName='" + databaseName + '\'' +
        ", datasetName='" + datasetName + '\'' +
        '}';
  }
}
