package com.bezkoder.spring.jwt.mongodb.security.services;

import com.bezkoder.spring.jwt.mongodb.models.Chart;
import com.bezkoder.spring.jwt.mongodb.models.ChartConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ChartService {

  public String findLoggedInUser() {
    Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String userId = ((UserDetailsImpl) principle).getId();

    return userId;
  }

  public Chart createChart(Chart chart) {
    String userId = findLoggedInUser();
    System.out.println(userId);

    Chart _chart = new Chart();

    _chart.setUserId(userId);
    _chart.setPivotId(chart.getPivotId());
    _chart.setName(chart.getName());
    _chart.setType(chart.getType());
    _chart.setRenderAt(chart.getRenderAt());
    _chart.setWidth(chart.getWidth());
    _chart.setHeight(chart.getHeight());

    ChartConfig _config = new ChartConfig();

    _config.setCaption(chart.getChartConfig().getCaption());
    _config.setSubcaption(chart.getChartConfig().getSubcaption());
    _config.setTheme(chart.getChartConfig().getTheme());

    _chart.setChartConfig(_config);

    return _chart;
  }
}
