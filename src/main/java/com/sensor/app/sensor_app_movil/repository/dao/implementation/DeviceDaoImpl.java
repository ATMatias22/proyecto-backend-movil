package com.sensor.app.sensor_app_movil.repository.dao.implementation;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.repository.IDeviceRepository;
import com.sensor.app.sensor_app_movil.repository.dao.IDeviceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
