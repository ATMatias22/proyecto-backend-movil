package com.sensor.app.sensor_app_movil.security.service;

import com.sensor.app.sensor_app_movil.security.entity.User;

public interface IAuthService {
    String login (User user);
    void register(User user);
}
