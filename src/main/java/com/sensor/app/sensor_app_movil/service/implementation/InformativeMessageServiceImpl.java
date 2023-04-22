package com.sensor.app.sensor_app_movil.service.implementation;


import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.repository.dao.IInformativeMessageDao;
import com.sensor.app.sensor_app_movil.service.IInformativeMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformativeMessageServiceImpl implements IInformativeMessageService {

    @Autowired
    private IInformativeMessageDao informativeMessageDao;

    @Override
    public void deleteByFkDevice(Device fkDevice) {
        this.informativeMessageDao.deleteByFkDevice(fkDevice);
    }
}
