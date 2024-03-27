package com.example.models.pivot;

import java.util.List;
import java.util.Map;

public class Slice {
  private List<Map<String,String>> rows;
  private List<Map<String,String>> columns;
  private List<Map<String,String>> measures;

  public Slice() {

  }

  public Slice(List<Map<String,String>> rows, List<Map<String,String>> columns,
      List<Map<String,String>> measures) {
    this.rows = rows;
    this.columns = columns;
    this.measures = measures;
  }

  public List<Map<String,String>> getRows() {
    return rows;
  }

  public void setRows(List<Map<String,String>> rows) {
    this.rows = rows;
  }

  public List<Map<String,String>> getColumns() {
    return columns;
  }

  public void setColumns(List<Map<String,String>> columns) {
    this.columns = columns;
  }

  public List<Map<String,String>> getMeasures() {
    return measures;
  }

  public void setMeasures(List<Map<String,String>> measures) {
    this.measures = measures;
  }
}
