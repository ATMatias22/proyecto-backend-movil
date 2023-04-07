package com.sensor.app.sensor_app_movil.security.repository.dao.implementation;


import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenEmailChange;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.repository.IConfirmationTokenEmailChangeRepository;
import com.sensor.app.sensor_app_movil.security.repository.dao.IConfirmationTokenEmailChangeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ConfirmationTokenEmailChangeDaoImpl  implements IConfirmationTokenEmailChangeDao {

    @Autowired
    IConfirmationTokenEmailChangeRepository confirmationTokenEmailChangeRepository;

    @Override
    public Optional<ConfirmationTokenEmailChange> getByToken(String token) {
        return this.confirmationTokenEmailChangeRepository.findByToken(token);
    }

    @Override
    public void saveConfirmationTokenEmailChange(ConfirmationTokenEmailChange cte) {
        this.confirmationTokenEmailChangeRepository.save(cte);
    }

    @Override
    public void deleteByToken(String token) {
        this.confirmationTokenEmailChangeRepository.deleteByToken(token);
    }

    @Override
    public Optional<ConfirmationTokenEmailChange> getTokenByUser(User user) {
        return this.confirmationTokenEmailChangeRepository.findByFkUser(user);
    }
}
