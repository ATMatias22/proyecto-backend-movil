package com.sensor.app.sensor_app_movil.service.implementation;


import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.Observer;
import com.sensor.app.sensor_app_movil.exception.GeneralException;
import com.sensor.app.sensor_app_movil.repository.dao.IObserverDao;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.service.IObserverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObserverServiceImpl implements IObserverService {

    @Autowired
    private IObserverDao observerDao;


    @Override
    public boolean existsByUserAndDevice(User fkUser, Device fkDevice) {
        return this.observerDao.existsByUserAndDevice(fkUser, fkDevice);
    }

    @Override
    public void save(Observer observer) {
        this.observerDao.save(observer);
    }

    @Override
    public void delete(Observer observer) {
        this.observerDao.delete(observer);
    }

    @Override
    public Observer getObserverByUserAndDevice(User user, Device device) {
        return this.observerDao.getObserverByUserAndDevice(user,device).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "No se encontro el invitado"));
    }

    @Override
    public List<Observer> getObserversByFkUser(User fkUser, Pageable pageable) {
        return this.observerDao.getObserversByFkUser(fkUser, pageable);
    }

    @Override
    public void deleteByFkDevice(Device fkDevice) {
        this.observerDao.deleteByFkDevice(fkDevice);
    }

    @Override
    public void deleteAllByFkUser(User fkUser) {
        this.observerDao.deleteAllByFkUser(fkUser);
    }

    @Override
    public int countByDevice(Device fkDevice) {
        return this.observerDao.countByDevice(fkDevice);
    }

    @Override
    public List<Observer> findByFkDevice(Device fkDevice) {
        return this.observerDao.findByFkDevice(fkDevice);
    }
}
