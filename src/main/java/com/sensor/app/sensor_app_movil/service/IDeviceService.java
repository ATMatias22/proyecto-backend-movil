package com.sensor.app.sensor_app_movil.service;


import com.sensor.app.sensor_app_movil.entity.Device;

public interface IDeviceService {


    void linkUser(String deviceCode, String password);

    Device getByDeviceCode(String deviceCode);
    void addObserver(String email, String deviceCode);

    void unlinkObserver(String deviceCode);

    void confirmationInvitation(String token);

    void deleteObserver(String deviceCode, String deletedUserEmail);




}
