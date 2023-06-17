package com.sensor.app.sensor_app_movil.dto.device.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurnOnDeviceRequest {

    @NotBlank(message = "El codigo no puede ser nulo ni vacio")
    private String deviceCode;
}
