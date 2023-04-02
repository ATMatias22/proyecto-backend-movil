package com.sensor.app.sensor_app_movil.security.repository;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {


    Optional<ConfirmationToken> findByToken(String token);

    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    Integer updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);

}
