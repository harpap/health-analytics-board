package com.example.controllers;

import com.example.models.UserCookiePreferences;
import com.example.repository.UserCookiePrefRepository;
import com.example.security.services.UserCookiePreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class UserCookiePreferencesController {

    private final UserCookiePrefRepository userCookiePrefRepository;
    private UserCookiePreferencesService userCookiePreferencesService;

    @Autowired
    public UserCookiePreferencesController(UserCookiePrefRepository userCookiePrefRepository,
        UserCookiePreferencesService userCookiePreferencesService) {
            this.userCookiePrefRepository = userCookiePrefRepository;
            this.userCookiePreferencesService = userCookiePreferencesService;
    }

    // Get cookie preferences for a specific user by userId
    @GetMapping("/cookies/{userId}")
    public ResponseEntity<UserCookiePreferences> getUserCookiePreferences(@PathVariable("userId") String userId) {
        Optional<UserCookiePreferences> preferences = userCookiePreferencesService.getPreferences(userId);
        if (preferences.isPresent()) {
            return ResponseEntity.ok(preferences.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create or update cookie preferences for a user
    @PostMapping("/cookies")
    public ResponseEntity<UserCookiePreferences> saveUserCookiePreferences(@RequestBody UserCookiePreferences userCookiePreferences) {
        UserCookiePreferences savedPreferences = userCookiePreferencesService.savePreferences(userCookiePreferences);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPreferences);
    }

    // Update cookie preferences for a user
    @PutMapping("/cookies/{id}")
    public ResponseEntity<UserCookiePreferences> updateUserCookiePreferences(@PathVariable("id") String id,
            @RequestBody UserCookiePreferences userCookiePreferences) {
        Optional<UserCookiePreferences> existingPreferences = userCookiePreferencesService.getPreferencesById(id);
        if (existingPreferences.isPresent()) {
            userCookiePreferences.setId(id); // Set the ID to ensure the update happens on the correct document
            UserCookiePreferences updatedPreferences = userCookiePreferencesService.savePreferences(userCookiePreferences);
            return ResponseEntity.ok(updatedPreferences);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete cookie preferences by ID
    @DeleteMapping("/cookies/{id}")
    public ResponseEntity<Void> deleteUserCookiePreferences(@PathVariable String id) {
        Optional<UserCookiePreferences> existingPreferences = userCookiePreferencesService.getPreferencesById(id);
        if (existingPreferences.isPresent()) {
            userCookiePreferencesService.deletePreferences(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
