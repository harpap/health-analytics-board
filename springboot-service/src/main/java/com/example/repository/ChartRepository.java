package com.example.repository;

import com.example.models.Chart;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChartRepository extends MongoRepository<Chart, String> {

  List<Chart> findByNameContaining(String name);

  List<Chart> findByUserId(String id);

  List<Chart> findByUserIdAndNameContaining(String userId, String name);

  void deleteByUserId(String userId);

  void deleteByIdAndUserId(String id, String userId);

}
