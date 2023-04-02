package com.sensor.app.sensor_app_movil.security.repository.dao;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationToken;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;

public interface IConfirmationTokenDao {

    Optional<ConfirmationToken> getConfirmationTokenByToken(String token);

    Integer setConfirmedAt(String  token, LocalDateTime confirmedAt);

    void saveConfirmationToken(ConfirmationToken token);
}
