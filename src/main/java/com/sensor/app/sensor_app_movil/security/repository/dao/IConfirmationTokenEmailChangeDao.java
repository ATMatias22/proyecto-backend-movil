package com.sensor.app.sensor_app_movil.security.repository.dao;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenEmailChange;
import com.sensor.app.sensor_app_movil.security.entity.User;

import java.util.Optional;

public interface IConfirmationTokenEmailChangeDao {


    Optional<ConfirmationTokenEmailChange> getByTokenAndFkUser(String token, User fkUser);

    void saveConfirmationTokenEmailChange(ConfirmationTokenEmailChange cte);

    void deleteByTokenAndFkUser(String token, User fkUser);

    Optional<ConfirmationTokenEmailChange> getTokenByUser(User user);


}
