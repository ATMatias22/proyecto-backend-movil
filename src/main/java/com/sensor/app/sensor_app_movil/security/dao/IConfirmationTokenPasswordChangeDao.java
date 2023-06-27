package com.sensor.app.sensor_app_movil.security.dao;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenPasswordChange;
import com.sensor.app.sensor_app_movil.security.entity.User;

import java.util.Optional;

public interface IConfirmationTokenPasswordChangeDao {

    Optional<ConfirmationTokenPasswordChange> getByTokenAndFkUser(String token, User fkUser);

    void saveConfirmationTokenPasswordChange(ConfirmationTokenPasswordChange ct);

    void deleteByTokenAndFkUser(String token, User fkUser);
    void deleteByFkUser(User fkUser);

    Optional<ConfirmationTokenPasswordChange> getTokenByUser(User user);





}
