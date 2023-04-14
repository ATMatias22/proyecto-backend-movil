package com.sensor.app.sensor_app_movil.service.implementation;

import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.exception.GeneralException;
import com.sensor.app.sensor_app_movil.repository.dao.IDeviceDao;
import com.sensor.app.sensor_app_movil.security.dto.MainUser;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.service.IUserService;
import com.sensor.app.sensor_app_movil.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class DeviceServiceImpl implements IDeviceService {

    @Autowired
    private IDeviceDao deviceDao;
    @Autowired
    private IUserService userService;
    private PasswordEncoder passwordEncoder;


    public DeviceServiceImpl(@Lazy PasswordEncoder passwordEncoder) {
      this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void bindUser(String deviceCode, String password) {

        Device device = this.getByDeviceCode(deviceCode);
        if(!passwordEncoder.matches(password, device.getPassword())){
            throw new GeneralException(HttpStatus.BAD_REQUEST, "Alguna de las credenciales del dispositivo es incorrecta");
        }

        if(device.getFkUser() != null){
            throw new GeneralException(HttpStatus.BAD_REQUEST, "El dispositivo en el que se quiere vincular, ya fue vinculado a otra persona");
        }
        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.getUserByEmail(mu.getUsername());
        device.setFkUser(user);
        this.deviceDao.save(device);
    }

    @Override
    public Device getByDeviceCode(String deviceCode) {
        return this.deviceDao.getByDeviceCode(deviceCode).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "No se encontro el dispositivo con codigo: " + deviceCode));
    }
}
