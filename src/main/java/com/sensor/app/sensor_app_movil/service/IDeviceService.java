package com.sensor.app.sensor_app_movil.service;


import com.sensor.app.sensor_app_movil.dto.device.response.ObservedDeviceResponse;
import com.sensor.app.sensor_app_movil.dto.device.response.OwnDeviceResponse;
import com.sensor.app.sensor_app_movil.dto.device.response.OwnDevicesResponse;
import com.sensor.app.sensor_app_movil.dto.device.response.DeviceStatusResponse;
import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.InformativeMessage;
import com.sensor.app.sensor_app_movil.entity.Observer;
import com.sensor.app.sensor_app_movil.security.entity.User;

import java.util.List;

public interface IDeviceService {


    void linkUser(String deviceCode, String password);

    OwnDeviceResponse getByDeviceCodeForOwner(String deviceCode);

    void addObserver(String email, String deviceCode);

    void unlinkObserver(String deviceCode);

    void confirmationInvitation(String token);

    void deleteObserver(String deviceCode, String deletedUserEmail);

    void clearHistory(String deviceCode);

    void deleteDeviceFromUser(String deviceCode);

    void deleteAllWhenDeleteUser(User user);

    List<OwnDevicesResponse> getAllByFkUser(int page);

    List<ObservedDeviceResponse> getAllByObserver(int page);

    void changeDeviceName(String deviceCode, String newName);

    void changeDevicePassword(String deviceCode, String oldPassword, String newPassword);

    void confirmTokenChangeDevicePassword(String token);

    List<InformativeMessage> getHistory(String deviceCode);

    List<Observer> getObserversOnTheDevice(String deviceCode);

    Device getByDeviceCode(String deviceCode);
    
    void changeWiFi(String deviceCode, String ssid, String password);

    DeviceStatusResponse turnOnDevice(String deviceCode);
    DeviceStatusResponse turnOffDevice(String deviceCode);


}
