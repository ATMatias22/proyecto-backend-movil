package com.sensor.app.sensor_app_movil.security.repository;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenPasswordChange;
import com.sensor.app.sensor_app_movil.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IConfirmationTokenPasswordChangeRepository extends JpaRepository<ConfirmationTokenPasswordChange, Long> {

    Optional<ConfirmationTokenPasswordChange> findByToken(String token);

    void deleteByToken(String token);

    Optional<ConfirmationTokenPasswordChange>  findByFkUser(User user);

}
