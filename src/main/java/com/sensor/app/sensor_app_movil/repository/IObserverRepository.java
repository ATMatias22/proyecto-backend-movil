package com.sensor.app.sensor_app_movil.repository;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.Observer;
import com.sensor.app.sensor_app_movil.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IObserverRepository extends JpaRepository<Observer, Long> {


    boolean existsByFkUserAndFkDevice(User fkUser, Device fkDevice);

    Optional<Observer> findByFkUserAndFkDevice(User fkUser, Device fkDevice);

}