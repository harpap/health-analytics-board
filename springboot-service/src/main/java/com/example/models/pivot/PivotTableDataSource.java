package com.example.models.pivot;

import com.example.models.Table;
import java.util.List;
import java.util.Map;

public class PivotTableDataSource {
  private List<Map<String, Object>> data;

  public PivotTableDataSource () {

  }

  public PivotTableDataSource(List<Map<String, Object>> data) {
    this.data = data;
  }

  public List<Map<String, Object>> getData() {
    return data;
  }

  public void setData(List<Map<String, Object>> data) {
    this.data = data;
  }
}
