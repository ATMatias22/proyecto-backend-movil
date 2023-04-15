package com.sensor.app.sensor_app_movil.service.implementation;


import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.Observer;
import com.sensor.app.sensor_app_movil.exception.GeneralException;
import com.sensor.app.sensor_app_movil.repository.dao.IObserverDao;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.service.IObserverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
}
