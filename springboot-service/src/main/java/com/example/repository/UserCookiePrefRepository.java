package com.example.repository;

import com.example.models.UserCookiePreferences;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserCookiePrefRepository extends MongoRepository<UserCookiePreferences, String> {
    
    // Custom method to find preferences by userId
    Optional<UserCookiePreferences> findByUserId(String userId);
    // Custom method to delete preferences by userId
    Optional<UserCookiePreferences> deleteByUserId(String userId);
}
