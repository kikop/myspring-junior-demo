package com.xxxkikop.service;

import com.xxxkikop.model.WelcomeProperties;


public class WelcomeService {

    private final WelcomeProperties welcomeProperties;

    public WelcomeService(WelcomeProperties welcomeProperties) {
        this.welcomeProperties = welcomeProperties;
    }

    public String getTagName() {
        return welcomeProperties.getName();
    }
}
