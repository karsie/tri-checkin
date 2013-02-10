package com.tricode.checkin.service;

import com.tricode.checkin.event.manager.EventManager;

public abstract class AbstractService {

    protected EventManager eventManager;

    public AbstractService(EventManager eventManager) {
        this.eventManager = eventManager;
    }
}
