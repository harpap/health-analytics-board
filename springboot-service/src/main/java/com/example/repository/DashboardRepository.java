package com.example.repository;

import com.example.models.Dashboard;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DashboardRepository extends MongoRepository<Dashboard, String> {

  List<Dashboard> findByUserIdAndNameContaining(String userId, String name);

  List<Dashboard> findByUserId(String id);

  List<Dashboard> findByNameContaining(String name);
}
