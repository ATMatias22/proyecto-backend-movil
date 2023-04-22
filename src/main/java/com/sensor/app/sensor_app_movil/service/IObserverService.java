package com.sensor.app.sensor_app_movil.service;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.Observer;
import com.sensor.app.sensor_app_movil.security.entity.User;

public interface IObserverService {

    boolean existsByUserAndDevice(User fkUser, Device fkDevice);
    void save (Observer observer);

    void delete (Observer observer);

    Observer getObserverByUserAndDevice(User user, Device device);

    void deleteByFkDevice(Device fkDevice);

}
