package com.sensor.app.sensor_app_movil.security.repository;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface IConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {


    Optional<ConfirmationToken> findByIdConfirmationToken(String id);


    @Modifying
    void deleteByIdConfirmationToken(String id);

}
