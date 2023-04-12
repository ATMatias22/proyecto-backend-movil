package com.sensor.app.sensor_app_movil.security.repository.dao;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenPasswordChange;
import com.sensor.app.sensor_app_movil.security.entity.User;

import java.util.Optional;

public interface IConfirmationTokenPasswordChangeDao {

    Optional<ConfirmationTokenPasswordChange> getByToken(String token);

    void saveConfirmationTokenPasswordChange(ConfirmationTokenPasswordChange ct);

    void deleteByToken(String token);

    Optional<ConfirmationTokenPasswordChange> getTokenByUser(User user);





}
