package com.bezkoder.spring.jwt.mongodb.security.services;

import com.bezkoder.spring.jwt.mongodb.models.Dashboard;
import com.bezkoder.spring.jwt.mongodb.models.Table;
import com.bezkoder.spring.jwt.mongodb.repository.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

  @Autowired
  private DashboardRepository dashboardRepository;

  public String findLoggedInUser() {
    Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String userId = ((UserDetailsImpl) principle).getId();

    return userId;
  }

  public Dashboard createDashboard(Dashboard dashboard) {
    String userId = findLoggedInUser();

    Dashboard _dashboard = new Dashboard();

    _dashboard.setUserId(userId);
    _dashboard.setName(dashboard.getName());
    _dashboard.setChartIds(dashboard.getChartIds());

    return _dashboard;
  }
}
