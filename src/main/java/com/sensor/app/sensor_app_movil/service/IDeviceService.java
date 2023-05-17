package com.sensor.app.sensor_app_movil.service;


import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.InformativeMessage;
import com.sensor.app.sensor_app_movil.entity.Observer;
import com.sensor.app.sensor_app_movil.security.entity.User;

import java.util.List;

public interface IDeviceService {


    void linkUser(String deviceCode, String password);

    Device getByDeviceCodeForOwner(String deviceCode);

    void addObserver(String email, String deviceCode);

    void unlinkObserver(String deviceCode);

    void confirmationInvitation(String token);

    void deleteObserver(String deviceCode, String deletedUserEmail);

    void clearHistory(String deviceCode);

    void deleteDeviceFromUser(String deviceCode);

    void deleteAllWhenDeleteUser(User user);

    List<Device> getAllByFkUser(int page);

    List<Device> getAllByObserver(int page);

    void changeDeviceName(String deviceCode, String newName);

    void changeDevicePassword(String deviceCode, String oldPassword, String newPassword);

    void confirmTokenChangeDevicePassword(String token);

    List<InformativeMessage> getHistory(String deviceCode);

    List<Observer> getObserversOnTheDevice(String deviceCode);


}
