package com.sensor.app.sensor_app_movil.security.service;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationToken;

import java.util.Optional;

public interface IConfirmationTokenService {

    void saveConfirmationToken(ConfirmationToken token);

    Optional<ConfirmationToken> getConfirmationTokenByToken(String token);

    Integer setConfirmedAt(String token);

}
