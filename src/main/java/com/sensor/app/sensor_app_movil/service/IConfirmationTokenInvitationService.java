package com.sensor.app.sensor_app_movil.service;

import com.sensor.app.sensor_app_movil.entity.ConfirmationTokenInvitation;
import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.security.entity.User;

public interface IConfirmationTokenInvitationService {


    ConfirmationTokenInvitation getByToken(String token);

    void deleteByToken(String token);

    ConfirmationTokenInvitation getByUserAndDevice(User user, Device device);

    void saveConfirmationTokenInvitation(ConfirmationTokenInvitation cte);

}
