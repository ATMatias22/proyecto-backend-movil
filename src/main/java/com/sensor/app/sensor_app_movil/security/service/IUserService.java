package com.sensor.app.sensor_app_movil.security.service;

import com.sensor.app.sensor_app_movil.security.entity.User;

public interface IUserService {


    User getUserByEmail(String email);

    void saveUser(User user);

    Integer enableUser(String email);

    void modifyPassword(String oldPassword, String newPassword);

}
