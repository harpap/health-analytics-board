package com.example.models.pivot;

public class PivotTableReport {
  private PivotTableDataSource dataSource;
  private Slice slice;

  public PivotTableReport() {

  }

  public PivotTableReport(PivotTableDataSource dataSource, Slice slice) {
    this.dataSource = dataSource;
    this.slice = slice;
  }

  public PivotTableDataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(PivotTableDataSource dataSource) {
    this.dataSource = dataSource;
  }

  public Slice getSlice() {
    return slice;
  }

  public void setSlice(Slice slice) {
    this.slice = slice;
  }
}
