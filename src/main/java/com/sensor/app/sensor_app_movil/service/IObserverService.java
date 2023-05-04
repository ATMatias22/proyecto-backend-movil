package com.sensor.app.sensor_app_movil.service;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.Observer;
import com.sensor.app.sensor_app_movil.security.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IObserverService {

    boolean existsByUserAndDevice(User fkUser, Device fkDevice);
    void save (Observer observer);

    void delete (Observer observer);

    Observer getObserverByUserAndDevice(User user, Device device);

    List<Observer> getObserversByFkUser(User fkUser, Pageable pageable);

    void deleteByFkDevice(Device fkDevice);

    void deleteAllByFkUser(User fkUser);

    int countByDevice(Device fkDevice);


}
