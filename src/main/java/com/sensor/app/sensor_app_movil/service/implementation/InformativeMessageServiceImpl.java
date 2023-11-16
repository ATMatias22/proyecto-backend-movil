package com.sensor.app.sensor_app_movil.service.implementation;


import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.InformativeMessage;
import com.sensor.app.sensor_app_movil.exception.GeneralException;
import com.sensor.app.sensor_app_movil.dao.IInformativeMessageDao;
import com.sensor.app.sensor_app_movil.service.IDeviceService;
import com.sensor.app.sensor_app_movil.service.IInformativeMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformativeMessageServiceImpl implements IInformativeMessageService {

    @Autowired
    private IInformativeMessageDao informativeMessageDao;

    private final IDeviceService deviceService;

    @Value("${arduino.secret}")
    private String arduinoSecret;

    public InformativeMessageServiceImpl(@Lazy IDeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void deleteByFkDevice(Device fkDevice) {
        this.informativeMessageDao.deleteByFkDevice(fkDevice);
    }

    @Override
    public List<InformativeMessage> findByFkDevice(Device fkDevice) {
        return this.informativeMessageDao.findByFkDevice(fkDevice);
    }

    @Override
    public void save(String deviceCode, String message, String token) {

        Device device = this.deviceService.getByDeviceCode(deviceCode);

        if (device.getFkUser() == null) {
            throw new GeneralException(HttpStatus.BAD_REQUEST, "El dispositivo no tiene dueño");
        } else {

            if(token.equals(arduinoSecret)){
                InformativeMessage informativeMesage = new InformativeMessage();
                informativeMesage.setMessage(message);
                informativeMesage.setFkDevice(device);
                this.informativeMessageDao.save(informativeMesage);
            }else{
                throw new GeneralException(HttpStatus.FORBIDDEN, "No puedes interactuar con este endpoint");
            }

        }
    }
}
