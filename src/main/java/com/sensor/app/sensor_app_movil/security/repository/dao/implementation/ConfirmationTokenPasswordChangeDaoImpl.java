package com.sensor.app.sensor_app_movil.security.repository.dao.implementation;


import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenPasswordChange;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.repository.IConfirmationTokenPasswordChangeRepository;
import com.sensor.app.sensor_app_movil.security.repository.dao.IConfirmationTokenPasswordChangeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmationTokenPasswordChangeDaoImpl implements IConfirmationTokenPasswordChangeDao {

    @Autowired
    IConfirmationTokenPasswordChangeRepository confirmationTokenPasswordChangeRepository;


    @Override
    public Optional<ConfirmationTokenPasswordChange> getByToken(String token) {
        return this.confirmationTokenPasswordChangeRepository.findByToken(token);
    }

    @Override
    public void saveConfirmationTokenPasswordChange(ConfirmationTokenPasswordChange ct) {
        this.confirmationTokenPasswordChangeRepository.save(ct);
    }

    @Override
    public void deleteByToken(String token) {
        this.confirmationTokenPasswordChangeRepository.deleteByToken(token);
    }

    @Override
    public Optional<ConfirmationTokenPasswordChange> getTokenByUser(User user) {
        return this.confirmationTokenPasswordChangeRepository.findByFkUser(user);
    }
}
