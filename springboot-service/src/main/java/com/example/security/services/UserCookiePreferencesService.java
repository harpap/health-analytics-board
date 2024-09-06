package com.example.security.services;

import com.example.models.UserCookiePreferences;
import com.example.repository.UserCookiePrefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCookiePreferencesService {

    @Autowired
    private UserCookiePrefRepository repository;

    public Optional<UserCookiePreferences> getPreferences(String userId) {
        return repository.findByUserId(userId);
    }

    public Optional<UserCookiePreferences> getPreferencesById(String id) {
        return repository.findById(id);
    }

    public UserCookiePreferences savePreferences(UserCookiePreferences preferences) {
        return repository.save(preferences);
    }

    public void deletePreferences(String userId) {
        repository.deleteByUserId(userId);
    }
}
