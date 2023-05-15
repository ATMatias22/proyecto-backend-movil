package com.sensor.app.sensor_app_movil.repository;

import com.sensor.app.sensor_app_movil.entity.ConfirmationTokenInvitation;
import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IConfirmationTokenInvitationRepository extends JpaRepository<ConfirmationTokenInvitation, Long> {


    Optional<ConfirmationTokenInvitation> findByToken(String token);

    void deleteByToken(String token);

    void deleteByFkUser(User user);

    void deleteByFkDevice(Device device);

    Optional<ConfirmationTokenInvitation>  findByFkUserAndFkDevice(User user, Device device);

}
