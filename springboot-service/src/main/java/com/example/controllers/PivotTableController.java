package com.example.controllers;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.ResourceOwnershipException;
import com.example.models.pivot.PivotTable;
import com.example.repository.PivotTableRepository;
import com.example.security.services.PivotTableService;
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
public class PivotTableController {

  private final PivotTableRepository pivotTableRepository;
  private final PivotTableService pivotTableService;

  @Autowired
  public PivotTableController(PivotTableRepository pivotTableRepository,
      PivotTableService pivotTableService) {
    this.pivotTableRepository = pivotTableRepository;
    this.pivotTableService = pivotTableService;
  }

  @PostMapping("/pivots")
  public ResponseEntity<PivotTable> createTable(@RequestBody PivotTable pivotTable) {
    try {
      PivotTable _pivotTable = pivotTableService.createPivotTable(pivotTable);
      _pivotTable = pivotTableRepository.save(_pivotTable);
      return new ResponseEntity<>(_pivotTable, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/pivots")
  public ResponseEntity<List<PivotTable>> getAllPivotTables(
      @RequestParam(required = false) String name) {

    String userId = pivotTableService.findLoggedInUser();

    try {
      List<PivotTable> pivotTables = new ArrayList<>();

      if (name == null) {
        Iterable<PivotTable> iterable = pivotTableRepository.findByUserId(userId);

        for (PivotTable pivotTable : iterable) {
          pivotTables.add(pivotTable);
        }
      } else {
        Iterable<PivotTable> iterable = pivotTableRepository.findByUserIdAndNameContaining(userId,
            name);

        for (PivotTable pivotTable : iterable) {
          pivotTables.add(pivotTable);
        }
      }

      if (pivotTables.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(pivotTables, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/pivots/{id}")
  public ResponseEntity<PivotTable> getTableById(@PathVariable("id") String id) {
    String userId = pivotTableService.findLoggedInUser();

    PivotTable _pivotTable = pivotTableRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Pivot Table with id = " + id));

    if (!_pivotTable.getUserId().contentEquals(userId)) {
      throw new ResourceOwnershipException(
          "You do not have permission to access Pivot Table with id = " + id);
    }

    return new ResponseEntity<>(_pivotTable, HttpStatus.OK);
  }

  @PutMapping("/pivots/{id}")
  public ResponseEntity<PivotTable> updatePivotTable(@PathVariable("id") String id,
      @RequestBody PivotTable pivotTable) {
    String userId = pivotTableService.findLoggedInUser();

    PivotTable _pivotTable = pivotTableRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Pivot Table with id = " + id));

    if (!_pivotTable.getUserId().contentEquals(userId)) {
      throw new ResourceOwnershipException(
          "You do not have permission to modify Pivot Table with id = " + id);
    }

    _pivotTable.setName(pivotTable.getName());

    return new ResponseEntity<>(pivotTableRepository.save(_pivotTable), HttpStatus.OK);
  }

  @DeleteMapping("/pivots/{id}")
  public ResponseEntity<HttpStatus> deletePivotTable(@PathVariable("id") String id) {
    String userId = pivotTableService.findLoggedInUser();

    PivotTable pivotTable = pivotTableRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Pivot Table with id = " + id));

    if (!pivotTable.getUserId().contentEquals(userId)) {
      throw new ResourceOwnershipException(
          "You do not have permission to modify Pivot Table with id = " + id);
    }

    try {
      pivotTableRepository.deleteByIdAndUserId(id, userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/pivots")
  public ResponseEntity<HttpStatus> deleteAllPivotTables() {
    String userId = pivotTableService.findLoggedInUser();

    try {
      pivotTableRepository.deleteByUserId(userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
