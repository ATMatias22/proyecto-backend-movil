package com.sensor.app.sensor_app_movil.security.service;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenEmailChange;
import com.sensor.app.sensor_app_movil.security.entity.User;

public interface IConfirmationTokenEmailChangeService {

    ConfirmationTokenEmailChange getConfirmationTokenEmailChangeByTokenAndFkUser(String token, User fkUser);

    void saveConfirmationTokenEmailChange(ConfirmationTokenEmailChange cte);

    void deleteByTokenAndFkUser (String token, User fkUser);

    ConfirmationTokenEmailChange getConfirmationTokenEmailChangeByUser(User user);

}
