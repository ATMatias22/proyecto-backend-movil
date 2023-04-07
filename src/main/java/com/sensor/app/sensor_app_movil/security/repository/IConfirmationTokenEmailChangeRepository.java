package com.sensor.app.sensor_app_movil.security.repository;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenEmailChange;
import com.sensor.app.sensor_app_movil.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IConfirmationTokenEmailChangeRepository extends JpaRepository<ConfirmationTokenEmailChange, Long> {

    Optional<ConfirmationTokenEmailChange> findByToken(String token);

    void deleteByToken(String token);

    Optional<ConfirmationTokenEmailChange>  findByFkUser(User user);





}
