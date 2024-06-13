package com.example.controllers;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.ResourceOwnershipException;
import com.example.models.Chart;
import com.example.models.Dashboard;
import com.example.models.Table;
import com.example.repository.DashboardRepository;
import com.example.security.services.DashboardService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class DashboardController {

  @Autowired
  DashboardRepository dashboardRepository;

  @Autowired
  DashboardService dashboardService;

  @GetMapping("/dashboards")
  public ResponseEntity<List<Dashboard>> getAllDashboards(
      @RequestParam(required = false) String name) {

    String userId = dashboardService.findLoggedInUser();
    try {
      List<Dashboard> dashboards = new ArrayList<>();

      if (name == null) {
        Iterable<Dashboard> iterable = dashboardRepository.findByUserId(userId);

        for (Dashboard dashboard : iterable) {
          dashboards.add(dashboard);
        }
      } else {
        Iterable<Dashboard> iterable = dashboardRepository.findByUserIdAndNameContaining(userId, name);;

        for (Dashboard dashboard : iterable) {
          dashboards.add(dashboard);
        }
      }

      if (dashboards.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(dashboards, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/dashboards/{id}")
  public ResponseEntity<Dashboard> getDatasetById(@PathVariable("id") String id) {
    String userId = dashboardService.findLoggedInUser();

    Dashboard _dashboard = dashboardRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Dashboard with id = " + id));

    if (!_dashboard.getUserId().contentEquals(userId)) {
      throw new ResourceOwnershipException(
          "You do not have permission to access Chart with id = " + id);
    }

    return new ResponseEntity<>(_dashboard, HttpStatus.OK);
  }

  @PostMapping("/dashboards")
  public ResponseEntity<Dashboard> createDashboard(@RequestBody Dashboard dashboard) {
    try {
      Dashboard _dashboard = dashboardService.createDashboard(dashboard);
      _dashboard = dashboardRepository.save(_dashboard);
      return new ResponseEntity<>(_dashboard, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/dashboards/{id}")
  public ResponseEntity<Dashboard> updateDashboard(@PathVariable("id") String id,
      @RequestBody Dashboard dashboard) {
    String userId = dashboardService.findLoggedInUser();

    Dashboard _dashboard = dashboardRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Dashboard with id = " + id));

    if (!_dashboard.getUserId().contentEquals(userId)) {
      throw new ResourceOwnershipException(
          "You do not have permission to modify Dashboard with id = " + id);
    }

    _dashboard.setName(dashboard.getName());
    _dashboard.setChartIds(dashboard.getChartIds());

    return new ResponseEntity<>(dashboardRepository.save(_dashboard), HttpStatus.OK);
  }

  @PatchMapping("/dashboards/{id}")
  public ResponseEntity<Dashboard> partialUpdateDashboard(@PathVariable("id") String id,
      @RequestBody Map<String, Object> updates) {
    String userId = dashboardService.findLoggedInUser();

    Dashboard _dashboard = dashboardRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Dashboard with id = " + id));

    if (!_dashboard.getUserId().contentEquals(userId)) {
      throw new ResourceOwnershipException(
          "You do not have permission to modify Dashboard with id = " + id);
    }

    // Update only the fields that are present in the updates map
    if (updates.containsKey("name")) {
      _dashboard.setName((String) updates.get("name"));
    }
    if (updates.containsKey("chartIds")) {
      _dashboard.setChartIds((List<String>) updates.get("chartIds"));
    }

    return new ResponseEntity<>(dashboardRepository.save(_dashboard), HttpStatus.OK);
  }

  @DeleteMapping("/dashboards/{id}")
  public ResponseEntity<HttpStatus> deleteDataset(@PathVariable("id") String id) {
    try {
      dashboardRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
