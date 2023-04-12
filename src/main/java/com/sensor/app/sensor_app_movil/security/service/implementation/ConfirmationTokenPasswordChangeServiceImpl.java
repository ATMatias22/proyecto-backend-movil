package com.sensor.app.sensor_app_movil.security.service.implementation;


import com.sensor.app.sensor_app_movil.exception.GeneralException;
import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenPasswordChange;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.repository.dao.IConfirmationTokenPasswordChangeDao;
import com.sensor.app.sensor_app_movil.security.service.IConfirmationTokenPasswordChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class ConfirmationTokenPasswordChangeServiceImpl implements IConfirmationTokenPasswordChangeService {

    @Autowired
    private IConfirmationTokenPasswordChangeDao confirmationTokenPasswordChangeDao;

    @Override
    public ConfirmationTokenPasswordChange getConfirmationTokenPasswordChangeByToken(String token) {
        return this.confirmationTokenPasswordChangeDao.getByToken(token).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "Token invalido"));

    }

    @Override
    public void saveConfirmationTokenPasswordChange(ConfirmationTokenPasswordChange ct) {
        this.confirmationTokenPasswordChangeDao.saveConfirmationTokenPasswordChange(ct);

    }

    @Override
    public void deleteByToken(String token) {
        this.confirmationTokenPasswordChangeDao.deleteByToken(token);
    }

    @Override
    public ConfirmationTokenPasswordChange getConfirmationTokenPasswordChangeByUser(User user) {
        return this.confirmationTokenPasswordChangeDao.getTokenByUser(user).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "Token no encontrado para este usuario"));
    }
}
