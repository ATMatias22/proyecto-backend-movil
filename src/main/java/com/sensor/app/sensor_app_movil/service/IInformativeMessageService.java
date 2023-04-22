package com.sensor.app.sensor_app_movil.service;

import com.sensor.app.sensor_app_movil.entity.Device;

public interface IInformativeMessageService {

    void deleteByFkDevice(Device fkDevice);

}
