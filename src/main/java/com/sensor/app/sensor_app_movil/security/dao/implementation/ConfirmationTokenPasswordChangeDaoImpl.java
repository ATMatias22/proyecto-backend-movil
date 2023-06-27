package com.sensor.app.sensor_app_movil.security.dao.implementation;


import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenPasswordChange;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.repository.IConfirmationTokenPasswordChangeRepository;
import com.sensor.app.sensor_app_movil.security.dao.IConfirmationTokenPasswordChangeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmationTokenPasswordChangeDaoImpl implements IConfirmationTokenPasswordChangeDao {

    @Autowired
    IConfirmationTokenPasswordChangeRepository confirmationTokenPasswordChangeRepository;


    @Override
    public Optional<ConfirmationTokenPasswordChange> getByTokenAndFkUser(String token, User fkUser) {
        return this.confirmationTokenPasswordChangeRepository.findByTokenAndFkUser(token,fkUser);
    }

    @Override
    public void saveConfirmationTokenPasswordChange(ConfirmationTokenPasswordChange ct) {
        this.confirmationTokenPasswordChangeRepository.save(ct);
    }

    @Override
    public void deleteByTokenAndFkUser(String token, User fkUser) {
        this.confirmationTokenPasswordChangeRepository.deleteByTokenAndFkUser(token, fkUser);
    }

    @Override
    public void deleteByFkUser(User fkUser) {
        this.confirmationTokenPasswordChangeRepository.deleteByFkUser(fkUser);
    }

    @Override
    public Optional<ConfirmationTokenPasswordChange> getTokenByUser(User user) {
        return this.confirmationTokenPasswordChangeRepository.findByFkUser(user);
    }
}
