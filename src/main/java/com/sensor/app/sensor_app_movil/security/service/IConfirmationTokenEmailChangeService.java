package com.sensor.app.sensor_app_movil.security.service;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenEmailChange;
import com.sensor.app.sensor_app_movil.security.entity.User;

public interface IConfirmationTokenEmailChangeService {

    ConfirmationTokenEmailChange getConfirmationTokenEmailChangeByToken(String token);

    void saveConfirmationTokenEmailChange(ConfirmationTokenEmailChange cte);

    void deleteByToken (String token);

    ConfirmationTokenEmailChange getConfirmationTokenEmailChangeByUser(User user);

}
