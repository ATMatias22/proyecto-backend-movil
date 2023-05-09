package com.sensor.app.sensor_app_movil.repository.dao;

import com.sensor.app.sensor_app_movil.entity.ConfirmationTokenDevicePasswordChange;
import com.sensor.app.sensor_app_movil.entity.Device;

import java.util.Optional;

public interface IConfirmationTokenDevicePasswordChangeDao {

    Optional<ConfirmationTokenDevicePasswordChange> getByToken(String token);

    void saveConfirmationTokenDevicePasswordChange(ConfirmationTokenDevicePasswordChange ct);

    void deleteByToken(String token);

    Optional<ConfirmationTokenDevicePasswordChange> getTokenByDevice(Device device);



}
