package com.sensor.app.sensor_app_movil.dto.arduino.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArduinoDeviceStatusResponse {

    private String message;
    private Boolean on;

}
