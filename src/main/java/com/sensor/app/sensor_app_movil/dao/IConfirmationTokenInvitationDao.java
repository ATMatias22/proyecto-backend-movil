package com.sensor.app.sensor_app_movil.dao;


import com.sensor.app.sensor_app_movil.entity.ConfirmationTokenInvitation;
import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.security.entity.User;

import java.util.Optional;

public interface IConfirmationTokenInvitationDao {

    Optional<ConfirmationTokenInvitation> getByToken(String token);

    void deleteByToken(String token);
    void deleteByFkUser(User user);

    void deleteByFkDevice(Device device);

    Optional<ConfirmationTokenInvitation>  getByUserAndDevice(User user, Device device);

    void saveConfirmationTokenInvitation (ConfirmationTokenInvitation cti);

}
