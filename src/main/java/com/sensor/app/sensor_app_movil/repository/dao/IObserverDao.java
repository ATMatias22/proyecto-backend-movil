package com.sensor.app.sensor_app_movil.repository.dao;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.Observer;
import com.sensor.app.sensor_app_movil.security.entity.User;

import java.util.Optional;

public interface IObserverDao{

    boolean existsByUserAndDevice(User fkUser, Device fkDevice);

    void save (Observer observer);

    void delete (Observer observer);

    Optional<Observer> getObserverByUserAndDevice(User user, Device device);

    void deleteByFkDevice(Device fkDevice);

}
