package com.example.repository;

import com.example.models.RefreshToken;
import com.example.models.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
  Optional<RefreshToken> findByToken(String token);

  int deleteByUser(User user);

  int findByUser(User user);
}
