package com.sensor.app.sensor_app_movil.service;

import com.sensor.app.sensor_app_movil.entity.ConfirmationTokenDevicePasswordChange;
import com.sensor.app.sensor_app_movil.entity.Device;

public interface IConfirmationTokenDevicePasswordChangeService {

    ConfirmationTokenDevicePasswordChange getConfirmationTokenDevicePasswordChangeByToken(String token);

    void saveConfirmationTokenDevicePasswordChange(ConfirmationTokenDevicePasswordChange ct);

    void deleteByToken (String token);

    ConfirmationTokenDevicePasswordChange getConfirmationTokenDevicePasswordChangeByDevice(Device device);
}
