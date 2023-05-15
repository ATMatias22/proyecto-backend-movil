package com.sensor.app.sensor_app_movil.service.implementation;

import com.sensor.app.sensor_app_movil.entity.ConfirmationTokenDevicePasswordChange;
import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.exception.GeneralException;
import com.sensor.app.sensor_app_movil.repository.dao.IConfirmationTokenDevicePasswordChangeDao;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.service.IConfirmationTokenDevicePasswordChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenDevicePasswordChangeServiceImpl implements IConfirmationTokenDevicePasswordChangeService {


    @Autowired
    private IConfirmationTokenDevicePasswordChangeDao confirmationTokenDevicePasswordChangeDao;

    @Override
    public ConfirmationTokenDevicePasswordChange getConfirmationTokenDevicePasswordChangeByToken(String token) {
        return this.confirmationTokenDevicePasswordChangeDao.getByToken(token).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "Token invalido"));
    }

    @Override
    public void saveConfirmationTokenDevicePasswordChange(ConfirmationTokenDevicePasswordChange ct) {
        this.confirmationTokenDevicePasswordChangeDao.saveConfirmationTokenDevicePasswordChange(ct);
    }

    @Override
    public void deleteByToken(String token) {
        this.confirmationTokenDevicePasswordChangeDao.deleteByToken(token);
    }

    @Override
    public void deleteByFkUser(User fkUser) {
        this.confirmationTokenDevicePasswordChangeDao.deleteByFkUser(fkUser);
    }

    @Override
    public ConfirmationTokenDevicePasswordChange getConfirmationTokenDevicePasswordChangeByDevice(Device device) {
        return this.confirmationTokenDevicePasswordChangeDao.getTokenByDevice(device).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "Token no encontrado para este dispositivo"));
    }
}
