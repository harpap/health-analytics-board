package com.example.repository;

import com.example.models.Dataset;
import com.example.projections.DatasetProjection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DatasetRepository extends MongoRepository<Dataset, String> {

  @Query(value = "{}", fields = "{ 'id' : 1, 'datasetName' : 1 }")
  List<DatasetProjection> findAllProjected();

  @Query(value = "{ 'datasetName' : { $regex: ?0, $options: 'i' } }", fields = "{ 'id' : 1, 'datasetName' : 1 }")
  List<DatasetProjection> findByNameContainingProjected(@Param("name") String name);
}
