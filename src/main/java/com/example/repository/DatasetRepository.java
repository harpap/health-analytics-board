package com.example.repository;

import com.example.models.Dataset;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DatasetRepository extends MongoRepository<Dataset, String> {

  List<Dataset> findByNameContaining(String name);
}
