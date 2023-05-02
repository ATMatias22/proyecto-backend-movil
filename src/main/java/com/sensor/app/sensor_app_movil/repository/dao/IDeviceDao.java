package com.sensor.app.sensor_app_movil.repository.dao;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.security.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IDeviceDao {

    Optional<Device> getByDeviceCode(String deviceCode);
    void save (Device device);
    List<Device> getAllByFkUser(User fkUser);
    List<Device> getAllByFkUser(User fkUser, Pageable pageable);
}
