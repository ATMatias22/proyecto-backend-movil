package com.sensor.app.sensor_app_movil.security.dao;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationToken;
import com.sensor.app.sensor_app_movil.security.entity.User;

import java.util.Optional;

public interface IConfirmationTokenDao {

    Optional<ConfirmationToken> getConfirmationTokenById(String id);

    void saveConfirmationToken(ConfirmationToken token);


    boolean existsTokenForFkUser (User user);

    void deleteById(String id);
}
