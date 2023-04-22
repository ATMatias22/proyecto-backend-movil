package com.sensor.app.sensor_app_movil.repository.dao;

import com.sensor.app.sensor_app_movil.entity.Device;

public interface IInformativeMessageDao {
    
    void deleteByFkDevice(Device fkDevice);
    
}
