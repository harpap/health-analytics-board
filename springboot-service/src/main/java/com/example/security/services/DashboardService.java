package com.example.security.services;

import com.example.models.Dashboard;
import com.example.models.Table;
import com.example.repository.DashboardRepository;
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
