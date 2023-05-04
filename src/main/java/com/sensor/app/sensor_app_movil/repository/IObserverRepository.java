package com.sensor.app.sensor_app_movil.repository;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.Observer;
import com.sensor.app.sensor_app_movil.security.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IObserverRepository extends JpaRepository<Observer, Long> {


    boolean existsByFkUserAndFkDevice(User fkUser, Device fkDevice);

    Optional<Observer> findByFkUserAndFkDevice(User fkUser, Device fkDevice);

    void deleteByFkDevice(Device fkDevice);
    void deleteByFkUser(User fkUser);

    int countByFkDevice(Device fkDevice);

    List<Observer> findByFkUser(User fkUser, Pageable pageable);

}
