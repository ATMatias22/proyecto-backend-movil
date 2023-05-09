package com.sensor.app.sensor_app_movil.repository.dao.implementation;

import com.sensor.app.sensor_app_movil.entity.ConfirmationTokenDevicePasswordChange;
import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.repository.IConfirmationTokenDevicePasswordChangeRepository;
import com.sensor.app.sensor_app_movil.repository.dao.IConfirmationTokenDevicePasswordChangeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ConfirmationTokenDevicePasswordChangeDaoImpl implements IConfirmationTokenDevicePasswordChangeDao {

    @Autowired
    private IConfirmationTokenDevicePasswordChangeRepository confirmationTokenDevicePasswordChangeRepository;


    @Override
    public Optional<ConfirmationTokenDevicePasswordChange> getByToken(String token) {
        return this.confirmationTokenDevicePasswordChangeRepository.findByToken(token);
    }

    @Override
    public void saveConfirmationTokenDevicePasswordChange(ConfirmationTokenDevicePasswordChange ct) {
        this.confirmationTokenDevicePasswordChangeRepository.save(ct);

    }

    @Override
    public void deleteByToken(String token) {
        this.confirmationTokenDevicePasswordChangeRepository.deleteByToken(token);
    }

    @Override
    public Optional<ConfirmationTokenDevicePasswordChange> getTokenByDevice(Device device) {
        return this.confirmationTokenDevicePasswordChangeRepository.findByFkDevice(device);
    }
}
