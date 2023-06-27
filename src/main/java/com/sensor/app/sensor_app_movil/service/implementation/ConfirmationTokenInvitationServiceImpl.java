package com.sensor.app.sensor_app_movil.service.implementation;

import com.sensor.app.sensor_app_movil.entity.ConfirmationTokenInvitation;
import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.exception.GeneralException;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.dao.IConfirmationTokenInvitationDao;
import com.sensor.app.sensor_app_movil.service.IConfirmationTokenInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenInvitationServiceImpl implements IConfirmationTokenInvitationService {

    @Autowired
    private IConfirmationTokenInvitationDao confirmationTokenInvitationDao;

    @Override
    public ConfirmationTokenInvitation getByToken(String token) {
        return this.confirmationTokenInvitationDao.getByToken(token).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "Token invalido"));

    }

    @Override
    public void deleteByToken(String token) {
        this.confirmationTokenInvitationDao.deleteByToken(token);
    }

    @Override
    public void deleteByFkUser(User fkUser) {
        this.confirmationTokenInvitationDao.deleteByFkUser(fkUser);
    }

    @Override
    public void deleteByFkDevice(Device fkDevice) {
        this.confirmationTokenInvitationDao.deleteByFkDevice(fkDevice);
    }

    @Override
    public ConfirmationTokenInvitation getByUserAndDevice(User user, Device device) {
        return this.confirmationTokenInvitationDao.getByUserAndDevice(user,device).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "Token no encontrado para este usuario en este dispositivo"));
    }

    @Override
    public void saveConfirmationTokenInvitation(ConfirmationTokenInvitation cte) {
        this.confirmationTokenInvitationDao.saveConfirmationTokenInvitation(cte);
    }
}
