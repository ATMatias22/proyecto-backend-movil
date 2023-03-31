package com.sensor.app.sensor_app_movil.security.repository.dao;

import com.sensor.app.sensor_app_movil.security.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserDao {

    Optional<User> getUser(Integer id);

    Optional<User> getUserByEmail(String email);
    User saveUser(User user);



}
