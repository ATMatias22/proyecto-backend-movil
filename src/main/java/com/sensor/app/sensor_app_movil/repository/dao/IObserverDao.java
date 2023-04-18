package com.sensor.app.sensor_app_movil.repository.dao;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.Observer;
import com.sensor.app.sensor_app_movil.security.entity.User;

public interface IObserverDao{

    boolean existsByUserAndDevice(User fkUser, Device fkDevice);

    void save (Observer observer);

}
