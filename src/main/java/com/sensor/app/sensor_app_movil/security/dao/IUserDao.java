package com.sensor.app.sensor_app_movil.security.dao;

import com.sensor.app.sensor_app_movil.security.entity.User;

import java.util.Optional;

public interface IUserDao {

    Optional<User> getUser(Long id);

    Optional<User> getUserByEmail(String email);
    User saveUser(User user);

    Integer enableUser(String email);

    void deleteUser(Long id);

    Optional<User> getUserById(Long id);



}
