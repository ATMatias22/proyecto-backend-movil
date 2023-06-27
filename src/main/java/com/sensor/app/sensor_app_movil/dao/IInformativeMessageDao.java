package com.sensor.app.sensor_app_movil.dao;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.InformativeMessage;

import java.util.List;

public interface IInformativeMessageDao {
    
    void deleteByFkDevice(Device fkDevice);

    List<InformativeMessage> findByFkDevice(Device fkDevice);


    void save(InformativeMessage informativeMessage);
    
}
