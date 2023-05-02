package com.sensor.app.sensor_app_movil.service;


import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.security.entity.User;

import java.util.List;

public interface IDeviceService {


    void linkUser(String deviceCode, String password);

    Device getByDeviceCode(String deviceCode);
    void addObserver(String email, String deviceCode);

    void unlinkObserver(String deviceCode);

    void confirmationInvitation(String token);

    void deleteObserver(String deviceCode, String deletedUserEmail);

    void clearHistory(String deviceCode);

    void deleteDeviceFromUser(String deviceCode);

    void deleteAllWhenDeleteUser(User user);

    List<Device> getAllByFkUser(int page);






}
