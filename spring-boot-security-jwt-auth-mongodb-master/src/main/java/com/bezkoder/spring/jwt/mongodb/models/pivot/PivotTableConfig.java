package com.bezkoder.spring.jwt.mongodb.models.pivot;

public class PivotTableConfig {

  private String container;
  private boolean toolbar;
  private String height;
  private String width;
  private PivotTableReport report;

  public PivotTableConfig() {

  }

  public PivotTableConfig(String container, boolean toolbar, String height, String width,
      PivotTableReport report) {
    this.container = container;
    this.toolbar = toolbar;
    this.height = height;
    this.width = width;
    this.report = report;
  }

  public String getContainer() {
    return container;
  }

  public void setContainer(String container) {
    this.container = container;
  }

  public boolean getToolbar() {
    return toolbar;
  }

  public void setToolbar(boolean toolbar) {
    this.toolbar = toolbar;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public String getWidth() {
    return width;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public PivotTableReport getReport() {
    return report;
  }

  public void setReport(PivotTableReport report) {
    this.report = report;
  }
}
