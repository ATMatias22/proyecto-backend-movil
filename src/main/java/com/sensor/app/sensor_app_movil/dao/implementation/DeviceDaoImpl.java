package com.sensor.app.sensor_app_movil.dao.implementation;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.repository.IDeviceRepository;
import com.sensor.app.sensor_app_movil.dao.IDeviceDao;
import com.sensor.app.sensor_app_movil.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public class DeviceDaoImpl implements IDeviceDao {

    @Autowired
    private IDeviceRepository deviceRepository;


    @Override
    public Optional<Device> getByDeviceCode(String deviceCode) {
        return this.deviceRepository.findByDeviceCode(deviceCode);
    }

    @Override
    public void save(Device device) {
         this.deviceRepository.save(device);
    }

    @Override
    public List<Device> getAllByFkUser(User fkUser) {
        return this.deviceRepository.findByFkUser(fkUser);
    }

    @Override
    public List<Device> getAllByFkUser(User fkUser, Pageable pageable) {
        return this.deviceRepository.findByFkUser(fkUser, pageable);
    }
}
