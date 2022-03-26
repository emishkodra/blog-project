package com.dev.service;

import com.dev.service.impl.NotificationServiceImpl;

public interface NotificationService {

    void addInfoMessage(String message);
    void addErrorMessage(String message);

}
