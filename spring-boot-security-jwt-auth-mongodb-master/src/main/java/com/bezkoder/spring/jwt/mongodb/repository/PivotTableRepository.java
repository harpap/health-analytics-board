package com.bezkoder.spring.jwt.mongodb.repository;

import com.bezkoder.spring.jwt.mongodb.models.Table;
import com.bezkoder.spring.jwt.mongodb.models.pivot.PivotTable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PivotTableRepository extends MongoRepository<PivotTable, String> {
  Optional<PivotTable> findByName(String name);

  List<PivotTable> findByNameContaining(String name);

  List<PivotTable> findByUserIdAndNameContaining(String userId, String name);

  List<PivotTable> findByUserId(String id);

  void deleteByUserId(String userId);

  void deleteByIdAndUserId(String id, String userId);
}
