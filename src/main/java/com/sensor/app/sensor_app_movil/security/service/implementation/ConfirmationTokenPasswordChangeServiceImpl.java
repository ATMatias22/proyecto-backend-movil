package com.sensor.app.sensor_app_movil.security.service.implementation;


import com.sensor.app.sensor_app_movil.exception.GeneralException;
import com.sensor.app.sensor_app_movil.security.entity.ConfirmationTokenPasswordChange;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.dao.IConfirmationTokenPasswordChangeDao;
import com.sensor.app.sensor_app_movil.security.service.IConfirmationTokenPasswordChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class ConfirmationTokenPasswordChangeServiceImpl implements IConfirmationTokenPasswordChangeService {

    @Autowired
    private IConfirmationTokenPasswordChangeDao confirmationTokenPasswordChangeDao;

    @Override
    public ConfirmationTokenPasswordChange getConfirmationTokenPasswordChangeByTokenAndFkUser(String token, User fkUser) {
        return this.confirmationTokenPasswordChangeDao.getByTokenAndFkUser(token,fkUser).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "No se encontro el token para este usuario"));
    }

    @Override
    public void saveConfirmationTokenPasswordChange(ConfirmationTokenPasswordChange ct) {
        this.confirmationTokenPasswordChangeDao.saveConfirmationTokenPasswordChange(ct);

    }

    @Override
    public void deleteByTokenAndFkUser(String token, User fkUser) {
        this.confirmationTokenPasswordChangeDao.deleteByTokenAndFkUser(token, fkUser);
    }


    @Override
    public void deleteByFkUser(User fkUser) {
        this.confirmationTokenPasswordChangeDao.deleteByFkUser(fkUser);
    }

    @Override
    public ConfirmationTokenPasswordChange getConfirmationTokenPasswordChangeByUser(User user) {
        return this.confirmationTokenPasswordChangeDao.getTokenByUser(user).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "Token no encontrado para este usuario"));
    }
}
