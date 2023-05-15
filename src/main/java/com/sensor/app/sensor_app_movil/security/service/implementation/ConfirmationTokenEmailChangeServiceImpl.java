package com.sensor.app.sensor_app_movil.security.service.implementation;

import com.sensor.app.sensor_app_movil.exception.GeneralException;
import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenEmailChange;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.repository.dao.IConfirmationTokenEmailChangeDao;
import com.sensor.app.sensor_app_movil.security.service.IConfirmationTokenEmailChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenEmailChangeServiceImpl implements IConfirmationTokenEmailChangeService {


    @Autowired
    private IConfirmationTokenEmailChangeDao confirmationTokenEmailChangeDao;

    @Override
    public ConfirmationTokenEmailChange getConfirmationTokenEmailChangeByTokenAndFkUser(String token, User fkUser) {
        return this.confirmationTokenEmailChangeDao.getByTokenAndFkUser(token, fkUser).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "Token invalido"));
    }

    @Override
    public void saveConfirmationTokenEmailChange(ConfirmationTokenEmailChange cte) {
        this.confirmationTokenEmailChangeDao.saveConfirmationTokenEmailChange(cte);
    }

    @Override
    public void deleteByTokenAndFkUser(String token, User fkUser) {
        this.confirmationTokenEmailChangeDao.deleteByTokenAndFkUser(token, fkUser);
    }

    @Override
    public void deleteByFkUser(User fkUser) {
        this.confirmationTokenEmailChangeDao.deleteByFkUser( fkUser);

    }

    @Override
    public ConfirmationTokenEmailChange getConfirmationTokenEmailChangeByUser(User user) {
        return this.confirmationTokenEmailChangeDao.getTokenByUser(user).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "Token no encontrado para este usuario"));
    }
}
