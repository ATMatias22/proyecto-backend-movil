package com.sensor.app.sensor_app_movil.service;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.InformativeMessage;

import java.util.List;

public interface IInformativeMessageService {

    void deleteByFkDevice(Device fkDevice);

    List<InformativeMessage> findByFkDevice(Device fkDevice);

}
