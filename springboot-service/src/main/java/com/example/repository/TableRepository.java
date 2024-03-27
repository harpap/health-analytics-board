package com.example.repository;

import com.example.models.Table;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TableRepository extends MongoRepository<Table, String> {

  Optional<Table> findByName(String name);

  List<Table> findByNameContaining(String name);

  List<Table> findByUserIdAndNameContaining(String userId, String name);

  List<Table> findByUserId(String id);

  void deleteByUserId(String userId);

  void deleteByIdAndUserId(String id, String userId);
}
