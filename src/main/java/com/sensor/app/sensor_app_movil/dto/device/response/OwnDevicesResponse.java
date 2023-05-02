package com.sensor.app.sensor_app_movil.dto.device.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnDevicesResponse {


    private String deviceName;
    private String deviceCode;
    private int linkedPersons;
    private Boolean isDeviceOn;
    private Boolean isWifiOn;

}
