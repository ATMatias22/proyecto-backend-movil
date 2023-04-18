package com.sensor.app.sensor_app_movil.repository.dao.implementation;

import com.sensor.app.sensor_app_movil.entity.ConfirmationTokenInvitation;
import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.repository.IConfirmationTokenInvitationRepository;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.repository.dao.IConfirmationTokenInvitationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ConfirmationTokenInvitationDaoImpl implements IConfirmationTokenInvitationDao {


    @Autowired
    private IConfirmationTokenInvitationRepository confirmationTokenInvitationRepository;

    @Override
    public Optional<ConfirmationTokenInvitation> getByToken(String token) {
        return this.confirmationTokenInvitationRepository.findByToken(token);
    }

    @Override
    public void deleteByToken(String token) {
        this.confirmationTokenInvitationRepository.deleteByToken(token);
    }

    @Override
    public Optional<ConfirmationTokenInvitation> getByUserAndDevice(User user, Device device) {
        return this.confirmationTokenInvitationRepository.findByFkUserAndFkDevice(user, device);

    }

    @Override
    public void saveConfirmationTokenInvitation(ConfirmationTokenInvitation cti) {
        this.confirmationTokenInvitationRepository.save(cti);
    }
}
