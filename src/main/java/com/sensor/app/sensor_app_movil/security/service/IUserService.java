package com.sensor.app.sensor_app_movil.security.service;

import com.sensor.app.sensor_app_movil.security.entity.User;

public interface IUserService {


    User getUserByEmail(String email);

    String saveUser(User user);

    Integer enableUser(String email);

}
