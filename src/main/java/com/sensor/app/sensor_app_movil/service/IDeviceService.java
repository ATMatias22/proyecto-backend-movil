package com.sensor.app.sensor_app_movil.service;


import com.sensor.app.sensor_app_movil.entity.Device;

public interface IDeviceService {


    void bindUser(String deviceCode, String password);

    Device getByDeviceCode(String deviceCode);




}
