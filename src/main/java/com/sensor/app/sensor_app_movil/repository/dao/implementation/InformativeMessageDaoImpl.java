package com.sensor.app.sensor_app_movil.repository.dao.implementation;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.repository.IInformativeMessageRepository;
import com.sensor.app.sensor_app_movil.repository.dao.IInformativeMessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InformativeMessageDaoImpl implements IInformativeMessageDao {

    @Autowired
    private IInformativeMessageRepository informativeMessageRepository;

    @Override
    public void deleteByFkDevice(Device fkDevice) {
        this.informativeMessageRepository.deleteByFkDevice(fkDevice);
    }
}
