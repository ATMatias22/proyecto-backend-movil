package com.sensor.app.sensor_app_movil.security.service;

import com.sensor.app.sensor_app_movil.security.entity.User;

public interface IUserService {


    User getUserByEmail(String email);

    void saveUser(User user);

    Integer enableUser(String email);

    void modifyPassword(String oldPassword, String newPassword);

    void confirmTokenEmailChange(String token);

    void modifyData(User modifiedUser);
    void confirmTokenPasswordChange (String token);

    void deleteUser(String password);

}
