package com.sensor.app.sensor_app_movil.security.service;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenPasswordChange;
import com.sensor.app.sensor_app_movil.security.entity.User;

public interface IConfirmationTokenPasswordChangeService {

    ConfirmationTokenPasswordChange getConfirmationTokenPasswordChangeByToken(String token);

    void saveConfirmationTokenPasswordChange(ConfirmationTokenPasswordChange ct);

    void deleteByToken (String token);

    ConfirmationTokenPasswordChange getConfirmationTokenPasswordChangeByUser(User user);

}
