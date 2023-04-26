package com.sensor.app.sensor_app_movil.repository;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDeviceRepository extends JpaRepository<Device, Long> {


    Optional<Device> findByDeviceCode(String deviceCode);

    List<Device> findByFkUser(User fkUser);


}
