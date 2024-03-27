package com.bezkoder.spring.jwt.mongodb.security.services;

import com.bezkoder.spring.jwt.mongodb.exception.ResourceNotFoundException;
import com.bezkoder.spring.jwt.mongodb.exception.ResourceOwnershipException;
import com.bezkoder.spring.jwt.mongodb.models.Table;
import com.bezkoder.spring.jwt.mongodb.models.pivot.PivotTable;
import com.bezkoder.spring.jwt.mongodb.models.pivot.PivotTableConfig;
import com.bezkoder.spring.jwt.mongodb.models.pivot.PivotTableDataSource;
import com.bezkoder.spring.jwt.mongodb.models.pivot.PivotTableReport;
import com.bezkoder.spring.jwt.mongodb.models.pivot.Slice;
import com.bezkoder.spring.jwt.mongodb.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PivotTableService {
  private TableRepository tableRepository;

  @Autowired
  public PivotTableService(TableRepository tableRepository) {
    this.tableRepository = tableRepository;
  }


  public String findLoggedInUser() {
    Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String userId = ((UserDetailsImpl) principle).getId();

    return userId;
  }

  public PivotTable createPivotTable(PivotTable pivotTable) {
    String userId = findLoggedInUser();
    String pivotTableId = pivotTable.getTableId();

    Table _table = tableRepository.findById(pivotTable.getTableId())
        .orElseThrow(() -> new ResourceNotFoundException("Not found Table with id = " + pivotTableId));

    if (!_table.getUserId().contentEquals(userId)) {
      throw new ResourceOwnershipException(
          "You do not have permission to access Table with id = " + pivotTableId);
    }

    // Create a PivotTable Object
    PivotTable _pivotTable = new PivotTable();

    // Set variables
    _pivotTable.setUserId(userId);
    _pivotTable.setTableId(pivotTable.getTableId());
    _pivotTable.setName(pivotTable.getName());

    // Create a PivotTableConfig
    PivotTableConfig pivotTableConfig = new PivotTableConfig();

    // Set variables
    pivotTableConfig.setContainer("#wdr-component");
    pivotTableConfig.setHeight(pivotTable.getConfig().getHeight());
    pivotTableConfig.setWidth(pivotTable.getConfig().getWidth());
    pivotTableConfig.setToolbar(pivotTable.getConfig().getToolbar());

    // Assign pivotTableConfig to _pivotTable
    _pivotTable.setConfig(pivotTableConfig);

    // Create PivotTableReport and PivotTableDataSource objects
    PivotTableReport pivotTableReport = new PivotTableReport();
    PivotTableDataSource pivotTableDataSource = new PivotTableDataSource();

    // Set variables
    pivotTableDataSource.setData(_table.getData());

    // Assign pivotTableDataSource to pivotTableReport
    pivotTableReport.setDataSource(pivotTableDataSource);

    // Create Slice object
    Slice slice = new Slice();

    // Set variables
    slice.setColumns(pivotTable.getConfig().getReport().getSlice().getColumns());
    slice.setRows(pivotTable.getConfig().getReport().getSlice().getRows());
    slice.setMeasures(pivotTable.getConfig().getReport().getSlice().getMeasures());

    // Assign slice to pivotTableReport
    pivotTableReport.setSlice(slice);

    // Assign pivotTableReport to pivotTableConfig
    pivotTableConfig.setReport(pivotTableReport);

    return _pivotTable;
  }
}
