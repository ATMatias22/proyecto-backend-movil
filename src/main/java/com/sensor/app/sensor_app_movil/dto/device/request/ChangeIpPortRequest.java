package com.sensor.app.sensor_app_movil.dto.device.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeIpPortRequest {

    private String ip;

    private String port;

    private String deviceCode;

}
