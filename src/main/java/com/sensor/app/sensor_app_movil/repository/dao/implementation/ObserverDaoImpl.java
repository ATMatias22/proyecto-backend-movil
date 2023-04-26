package com.sensor.app.sensor_app_movil.repository.dao.implementation;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.Observer;
import com.sensor.app.sensor_app_movil.repository.IObserverRepository;
import com.sensor.app.sensor_app_movil.repository.dao.IObserverDao;
import com.sensor.app.sensor_app_movil.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class ObserverDaoImpl implements IObserverDao {

    @Autowired
    private IObserverRepository observerRepository;

    @Override
    public boolean existsByUserAndDevice(User fkUser, Device fkDevice) {
        return this.observerRepository.existsByFkUserAndFkDevice(fkUser,fkDevice);
    }

    @Override
    public void save(Observer observer) {
        this.observerRepository.save(observer);
    }

    @Override
    public void delete(Observer observer) {
        this.observerRepository.delete(observer);
    }

    @Override
    public Optional<Observer> getObserverByUserAndDevice(User user, Device device) {
        return this.observerRepository.findByFkUserAndFkDevice(user,device);
    }

    @Override
    public void deleteByFkDevice(Device fkDevice) {
        this.observerRepository.deleteByFkDevice(fkDevice);
    }

    @Override
    public void deleteAllByFkUser(User fkUser) {
        this.observerRepository.deleteByFkUser(fkUser);
    }
}
