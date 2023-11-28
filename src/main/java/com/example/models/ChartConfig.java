package com.example.models;

public class ChartConfig {
  private String caption;
  private String subcaption;
  private String theme;

  public ChartConfig() {

  }

  public ChartConfig(String caption, String subcaption, String theme) {
    this.caption = caption;
    this.subcaption = subcaption;
    this.theme = theme;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public String getSubcaption() {
    return subcaption;
  }

  public void setSubcaption(String subcaption) {
    this.subcaption = subcaption;
  }

  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }
}
