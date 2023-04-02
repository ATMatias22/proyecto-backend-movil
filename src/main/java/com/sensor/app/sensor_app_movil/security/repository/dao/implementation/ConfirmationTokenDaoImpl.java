package com.sensor.app.sensor_app_movil.security.repository.dao.implementation;

import com.sensor.app.sensor_app_movil.security.entity.ConfirmationToken;
import com.sensor.app.sensor_app_movil.security.repository.IConfirmationTokenRepository;
import com.sensor.app.sensor_app_movil.security.repository.dao.IConfirmationTokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Optional;

@Repository
public class ConfirmationTokenDaoImpl implements IConfirmationTokenDao {

    @Autowired
    private IConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public Optional<ConfirmationToken> getConfirmationTokenByToken(String token) {
        return this.confirmationTokenRepository.findByToken(token);
    }

    @Override
    public Integer setConfirmedAt(String token, LocalDateTime confirmedAt) {
        return this.confirmationTokenRepository.updateConfirmedAt(token,confirmedAt);
    }

    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        this.confirmationTokenRepository.save(token);

    }
}
