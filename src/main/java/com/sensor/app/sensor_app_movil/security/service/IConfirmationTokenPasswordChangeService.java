package com.sensor.app.sensor_app_movil.security.service;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenPasswordChange;
import com.sensor.app.sensor_app_movil.security.entity.User;

public interface IConfirmationTokenPasswordChangeService {

    ConfirmationTokenPasswordChange getConfirmationTokenPasswordChangeByTokenAndFkUser(String token, User fkUser);

    void saveConfirmationTokenPasswordChange(ConfirmationTokenPasswordChange ct);

    void deleteByTokenAndFkUser (String token, User fkUser);

    void deleteByFkUser(User fkUser);

    ConfirmationTokenPasswordChange getConfirmationTokenPasswordChangeByUser(User user);

}
