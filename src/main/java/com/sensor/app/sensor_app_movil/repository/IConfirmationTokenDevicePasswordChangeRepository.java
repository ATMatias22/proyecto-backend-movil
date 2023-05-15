package com.sensor.app.sensor_app_movil.repository;

import com.sensor.app.sensor_app_movil.entity.ConfirmationTokenDevicePasswordChange;
import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IConfirmationTokenDevicePasswordChangeRepository extends JpaRepository<ConfirmationTokenDevicePasswordChange, Long> {

    Optional<ConfirmationTokenDevicePasswordChange> findByToken(String token);


    void deleteByToken(String token);
    void deleteByFkUser(User user);


    Optional<ConfirmationTokenDevicePasswordChange> findByFkDevice(Device device);


}
