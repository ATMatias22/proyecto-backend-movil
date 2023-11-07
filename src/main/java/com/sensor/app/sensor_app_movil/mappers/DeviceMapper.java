package com.sensor.app.sensor_app_movil.mappers;


import com.sensor.app.sensor_app_movil.dto.device.request.AddDeviceFromWebAdminRequest;
import com.sensor.app.sensor_app_movil.dto.device.response.ObservedDeviceResponse;
import com.sensor.app.sensor_app_movil.dto.device.response.OwnDeviceResponse;
import com.sensor.app.sensor_app_movil.dto.device.response.OwnDevicesResponse;
import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.service.IObserverService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")
public abstract class DeviceMapper {

    @Autowired
    protected IObserverService observerService;

    @Mappings({
            @Mapping(source = "device.name", target = "deviceName"),
            @Mapping(source = "device.deviceCode", target = "deviceCode"),
            @Mapping(target = "linkedPersons", expression = "java(observerService.countByDevice(device))" ),
            @Mapping(source = "onDevice", target = "isDeviceOn"),
            @Mapping(source = "onWifi", target = "isWifiOn"),
            @Mapping(source = "message", target = "message"),
    })
    public abstract OwnDevicesResponse toOwnDevicesResponse (Device device, Boolean onDevice, Boolean onWifi, String message);



    @Mappings({
            @Mapping(source = "device.name", target = "deviceName"),
            @Mapping(source = "device.deviceCode", target = "deviceCode"),
            @Mapping(source = "isDeviceOn", target = "isDeviceOn"),
            @Mapping(source = "isWifiOn", target = "isWifiOn"),
            @Mapping(source = "message", target = "message"),
    })
    public abstract OwnDeviceResponse toOwnDeviceResponse (Device device, Boolean isDeviceOn, Boolean isWifiOn, String message);


    @Mappings({
            @Mapping(source = "device.name", target = "deviceName"),
            @Mapping(source = "device.deviceCode", target = "deviceCode"),
            @Mapping(target = "device.linkedPersons", expression = "java(observerService.countByDevice(device))" ),
            @Mapping(source = "device.fkUser.email", target = "ownerEmail"),
            @Mapping(source = "onDevice", target = "isDeviceOn"),
            @Mapping(source = "onWifi", target = "isWifiOn"),
            @Mapping(source = "message", target = "message"),
    })
    public abstract ObservedDeviceResponse toObservedDeviceResponse (Device device, Boolean onDevice, Boolean onWifi, String message);


    @Mappings({
            @Mapping(source = "addDeviceFromWebAdminRequest.deviceCode", target = "deviceCode"),
            @Mapping(source = "addDeviceFromWebAdminRequest.password", target = "password"),
            @Mapping(target = "device.linkedPersons", expression = "java(observerService.countByDevice(device))" ),
            @Mapping(target = "idDevice", ignore = true),
            @Mapping(target = "name", ignore = true),
            @Mapping(target = "created", ignore = true),
            @Mapping(target = "updated", ignore = true),
            @Mapping(target = "fkUser", ignore = true),
    })
    public abstract Device addDeviceFromWebAdminRequestToDevice (AddDeviceFromWebAdminRequest addDeviceFromWebAdminRequest);



}
