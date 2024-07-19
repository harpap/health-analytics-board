package com.example.controllers;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.ResourceOwnershipException;
import com.example.models.Table;
import com.example.repository.TableRepository;
import com.example.security.services.TableService;
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
public class TableController {

  private final TableRepository tableRepository;
  private final TableService tableService;

  @Autowired
  public TableController(TableRepository tableRepository,
      TableService tableService) {
    this.tableRepository = tableRepository;
    this.tableService = tableService;
  }

  @GetMapping("/tables")
  public ResponseEntity<List<Table>> getAllTables(
      @RequestParam(required = false) String name) {

    String userId = tableService.findLoggedInUser();

    try {
      List<Table> tables = new ArrayList<>();

      if (name == null) {
        Iterable<Table> iterable = tableRepository.findByUserId(userId);

        for (Table table : iterable) {
          tables.add(table);
        }
      } else {
        Iterable<Table> iterable = tableRepository.findByUserIdAndNameContaining(userId, name);

        for (Table table : iterable) {
          tables.add(table);
        }
      }

      if (tables.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(tables, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/tables/{id}")
  public ResponseEntity<Table> getTableById(@PathVariable("id") String id) {
    String userId = tableService.findLoggedInUser();

    Table _table = tableRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Table with id = " + id));

    if (!_table.getUserId().contentEquals(userId)) {
      throw new ResourceOwnershipException(
          "You do not have permission to access Table with id = " + id);
    }

    return new ResponseEntity<>(_table, HttpStatus.OK);
  }

  @PostMapping("/tables")
  public ResponseEntity<Table> createTable(@RequestBody Table table) {
    try {
      Table _table = tableService.createTable(table);
      _table = tableRepository.save(_table);
      return new ResponseEntity<>(_table, HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println(e);
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/tables/{id}")
  public ResponseEntity<Table> updateTable(@PathVariable("id") String id,
      @RequestBody Table table) {
    String userId = tableService.findLoggedInUser();

    Table _table = tableRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Table with id = " + id));

    if (!_table.getUserId().contentEquals(userId)) {
      throw new ResourceOwnershipException(
          "You do not have permission to modify Table with id = " + id);
    }

    _table.setName(table.getName());
    _table.setSelectedColumns(table.getSelectedColumns());
    _table.setSelectedRows(table.getSelectedRows());
    _table.setData(table.getData());

    return new ResponseEntity<>(tableRepository.save(_table), HttpStatus.OK);
  }

  @DeleteMapping("/tables/{id}")
  public ResponseEntity<HttpStatus> deleteTable(@PathVariable("id") String id) {
    String userId = tableService.findLoggedInUser();

    Table _table = tableRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Table with id = " + id));

    if (!_table.getUserId().contentEquals(userId)) {
      throw new ResourceOwnershipException(
          "You do not have permission to modify Table with id = " + id);
    }

    try {
      tableRepository.deleteByIdAndUserId(id, userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/tables")
  public ResponseEntity<HttpStatus> deleteAllTables() {
    String userId = tableService.findLoggedInUser();

    try {
      tableRepository.deleteByUserId(userId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
