package com.sensor.app.sensor_app_movil.dao;

import com.sensor.app.sensor_app_movil.entity.ConfirmationTokenDevicePasswordChange;
import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.security.entity.User;

import java.util.Optional;

public interface IConfirmationTokenDevicePasswordChangeDao {

    Optional<ConfirmationTokenDevicePasswordChange> getByToken(String token);

    void saveConfirmationTokenDevicePasswordChange(ConfirmationTokenDevicePasswordChange ct);

    void deleteByToken(String token);
    void deleteByFkUser(User fkUser);

    Optional<ConfirmationTokenDevicePasswordChange> getTokenByDevice(Device device);



}
