package com.example.security.services;

import com.example.models.Dataset;
import com.example.models.Table;
import com.example.repository.DatasetRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TableService {

  @Autowired
  private DatasetRepository datasetRepository;

  public String findLoggedInUser() {
    Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String userId = ((UserDetailsImpl) principle).getId();

    return userId;
  }

  public Table createTable(Table table) {
    String userId = findLoggedInUser();

    Table _table = new Table();

    _table.setUserId(userId);
    _table.setName(table.getName());
    _table.setDatasetId(table.getDatasetId());

    List<String> selectedColumns = table.getSelectedColumns();
    _table.setSelectedColumns(selectedColumns);

    List<String> selectedCountries = table.getSelectedCountries();
    _table.setSelectedCountries(selectedCountries);

    Optional<Dataset> dataset = datasetRepository.findById(table.getDatasetId());
    List<Map<String, Object>> data = dataset.get().getData();

    if (selectedColumns.isEmpty() && selectedCountries.isEmpty()) {
      _table.setData(data);
    } else {
      List<Map<String, Object>> filteredColumns = new ArrayList<>();
      if (!selectedColumns.isEmpty()) {
        for (Map<String, Object> row : data) {
          Map<String, Object> filteredRow = row.entrySet().stream()
              .filter(entry -> selectedColumns.contains(entry.getKey()))
              .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

          filteredColumns.add(filteredRow);
        }

        data = filteredColumns;
      }

      List<Map<String, Object>> filteredCountries = new ArrayList<>();
      if (!selectedCountries.isEmpty()) {
        for (Map<String, Object> row : data) {
          boolean shouldIncludeRoe = row.entrySet().stream()
              .filter(entry -> "Country".equals(entry.getKey()))
              .anyMatch(entry -> selectedCountries.contains(entry.getValue()));

          if (shouldIncludeRoe) {
            filteredCountries.add(new HashMap<>(row));
          }
        }

        data = filteredCountries;
      }

      _table.setData(data);
    }

    return _table;
  }
}
