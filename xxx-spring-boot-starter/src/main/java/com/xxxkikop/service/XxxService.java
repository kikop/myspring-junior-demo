package com.xxxkikop.service;

import com.xxxkikop.model.XxxProperties;

public class XxxService {

    private final XxxProperties xxxProperties;

    public XxxService(XxxProperties xxxProperties) {
        this.xxxProperties = xxxProperties;
    }

    public void doSomeThing() {
        System.out.println(xxxProperties.getName());
    }
}
