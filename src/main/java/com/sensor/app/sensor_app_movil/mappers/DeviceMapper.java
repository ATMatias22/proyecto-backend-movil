package com.sensor.app.sensor_app_movil.mappers;


import com.sensor.app.sensor_app_movil.dto.device.OwnDevicesResponse;
import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.service.IObserverService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring")
public abstract class DeviceMapper {

    @Autowired
    protected IObserverService observerService;

    @Mappings({
            @Mapping(source = "name", target = "deviceName"),
            @Mapping(source = "deviceCode", target = "deviceCode"),
            @Mapping(target = "linkedPersons", expression = "java(observerService.countByDevice(device))" ),
            @Mapping(source = "on", target = "deviceStatus"),
            @Mapping(source = "device.fkWiFi.state", target = "deviceWifiState"),
    })
    public abstract OwnDevicesResponse toOwnDevicesResponse (Device device);




}
