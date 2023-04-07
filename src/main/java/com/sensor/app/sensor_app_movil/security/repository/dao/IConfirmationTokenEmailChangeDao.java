package com.sensor.app.sensor_app_movil.security.repository.dao;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenEmailChange;
import com.sensor.app.sensor_app_movil.security.entity.User;

import java.util.Optional;

public interface IConfirmationTokenEmailChangeDao {


    Optional<ConfirmationTokenEmailChange> getByToken(String token);

    void saveConfirmationTokenEmailChange(ConfirmationTokenEmailChange cte);

    void deleteByToken(String token);

    Optional<ConfirmationTokenEmailChange> getTokenByUser(User user);


}
