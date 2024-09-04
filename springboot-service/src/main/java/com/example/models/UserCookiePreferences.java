package com.example.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userCookiePreferences")
public class UserCookiePreferences {

    @Id
    private String id;

    private String userId;
    private boolean functionalCookies;
    private boolean analyticsCookies;
    private boolean marketingCookies;

    // Constructors
    public UserCookiePreferences() {
    }

    public UserCookiePreferences(String userId, boolean functionalCookies, boolean analyticsCookies, boolean marketingCookies) {
        this.userId = userId;
        this.functionalCookies = functionalCookies;
        this.analyticsCookies = analyticsCookies;
        this.marketingCookies = marketingCookies;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isFunctionalCookies() {
        return functionalCookies;
    }

    public void setFunctionalCookies(boolean functionalCookies) {
        this.functionalCookies = functionalCookies;
    }

    public boolean isAnalyticsCookies() {
        return analyticsCookies;
    }

    public void setAnalyticsCookies(boolean analyticsCookies) {
        this.analyticsCookies = analyticsCookies;
    }

    public boolean isMarketingCookies() {
        return marketingCookies;
    }

    public void setMarketingCookies(boolean marketingCookies) {
        this.marketingCookies = marketingCookies;
    }
}
