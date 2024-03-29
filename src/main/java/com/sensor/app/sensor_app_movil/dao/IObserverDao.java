package com.sensor.app.sensor_app_movil.dao;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.Observer;
import com.sensor.app.sensor_app_movil.security.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IObserverDao{

    boolean existsByUserAndDevice(User fkUser, Device fkDevice);

    void save (Observer observer);

    void delete (Observer observer);

    Optional<Observer> getObserverByUserAndDevice(User user, Device device);

    void deleteByFkDevice(Device fkDevice);

    void deleteAllByFkUser(User fkUser);

    int countByDevice(Device fkDevice);
    List<Observer> getObserversByFkUser(User fkUser, Pageable pageable);

    List<Observer> findByFkDevice(Device fkDevice);


}
