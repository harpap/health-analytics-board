package com.example.controllers;

import com.example.exception.ResourceNotFoundException;
import com.example.models.Dataset;
import com.example.projections.DatasetProjection;
import com.example.repository.DatasetRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class DatasetController {

  @Autowired
  DatasetRepository datasetRepository;

  @GetMapping("/datasets")
  public ResponseEntity<List<DatasetProjection>> getAllDatasets(@RequestParam(required = false) String name) {
    try {
      List<DatasetProjection> datasets;

      if (name == null) {
        datasets = datasetRepository.findAllProjected();
      } else {
        datasets = datasetRepository.findByNameContainingProjected(name);
      }

      if (datasets.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(datasets, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/datasets/{id}")
  public ResponseEntity<Dataset> getDatasetById(@PathVariable("id") String id) {
    Dataset datasetData = datasetRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Dataset with id = " + id));

    return new ResponseEntity<>(datasetData, HttpStatus.OK);
  }

  @PostMapping("/datasets")
  public ResponseEntity<List<Dataset>> createDatasets(@RequestBody List<Dataset> datasets) {
    try {
      List<Dataset> _datasets = datasetRepository.saveAll(datasets);
      return new ResponseEntity<>(_datasets, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/datasets/{id}")
  public ResponseEntity<Dataset> updateDataset(@PathVariable("id") String id,
      @RequestBody Dataset dataset) {
    Dataset _dataset = datasetRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Dataset with id = " + id));

    _dataset.setName(dataset.getName());
    _dataset.setCategory(dataset.getCategory());
    _dataset.setDatabaseName(dataset.getDatabaseName());
    _dataset.setDatasetName(dataset.getDatasetName());
    _dataset.setData(dataset.getData());

    return new ResponseEntity<>(datasetRepository.save(_dataset), HttpStatus.OK);
  }

  @DeleteMapping("/datasets/{id}")
  public ResponseEntity<HttpStatus> deleteDataset(@PathVariable("id") String id) {
    try {
      datasetRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/datasets")
  public ResponseEntity<HttpStatus> deleteAllDatasets() {
    try {
      datasetRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
