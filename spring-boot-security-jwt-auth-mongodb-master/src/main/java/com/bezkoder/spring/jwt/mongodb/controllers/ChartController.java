package com.bezkoder.spring.jwt.mongodb.controllers;

import com.bezkoder.spring.jwt.mongodb.exception.ResourceNotFoundException;
import com.bezkoder.spring.jwt.mongodb.exception.ResourceOwnershipException;
import com.bezkoder.spring.jwt.mongodb.models.Chart;
import com.bezkoder.spring.jwt.mongodb.models.Table;
import com.bezkoder.spring.jwt.mongodb.repository.ChartRepository;
import com.bezkoder.spring.jwt.mongodb.security.services.ChartService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class ChartController {

  @Autowired
  private ChartRepository chartRepository;

  @Autowired
  private ChartService chartService;

  @GetMapping("/charts")
  public ResponseEntity<List<Chart>> getAllCharts(
      @RequestParam(required = false) String name) {

    String userId = chartService.findLoggedInUser();

    try {
      List<Chart> charts = new ArrayList<>();

      if (name == null) {
        Iterable<Chart> iterable = chartRepository.findByUserId(userId);

        for (Chart chart : iterable) {
          charts.add(chart);
        }
      } else {
        Iterable<Chart> iterable = chartRepository.findByUserIdAndNameContaining(userId, name);

        for (Chart chart : iterable) {
          charts.add(chart);
        }
      }

      if (charts.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(charts, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/charts/{id}")
  public ResponseEntity<Chart> getChartById(@PathVariable("id") String id) {
    String userId = chartService.findLoggedInUser();

    Chart _chart = chartRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Chart with id = " + id));

    if (!_chart.getUserId().contentEquals(userId)) {
      throw new ResourceOwnershipException(
          "You do not have permission to access Chart with id = " + id);
    }

    return new ResponseEntity<>(_chart, HttpStatus.OK);
  }

  @PutMapping("/charts/{id}")
  public ResponseEntity<Chart> updateChart(@PathVariable("id") String id,
      @RequestBody Chart chart) {
    String userId = chartService.findLoggedInUser();

    Chart _chart = chartRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Chart with id = " + id));

    if (!_chart.getUserId().contentEquals(userId)) {
      throw new ResourceOwnershipException(
          "You do not have permission to modify Chart with id = " + id);
    }

    _chart.setName(chart.getName());

    return new ResponseEntity<>(chartRepository.save(_chart), HttpStatus.OK);
  }

  @DeleteMapping("/charts/{id}")
  public ResponseEntity<HttpStatus> deleteChart(@PathVariable("id") String id) {
    String userId = chartService.findLoggedInUser();

    Chart _chart = chartRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Chart with id = " + id));

    if (!_chart.getUserId().contentEquals(userId)) {
      throw new ResourceOwnershipException(
          "You do not have permission to modify Chart with id = " + id);
    }

    try {
      chartRepository.deleteByIdAndUserId(id, userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/charts")
  public ResponseEntity<HttpStatus> deleteAllCharts() {
    String userId = chartService.findLoggedInUser();

    try {
      chartRepository.deleteByUserId(userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/charts")
  public ResponseEntity<Chart> createChart(@RequestBody Chart chart) {

    try {
      Chart _chart = chartService.createChart(chart);
      _chart = chartRepository.save(_chart);
      return new ResponseEntity<>(_chart, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
