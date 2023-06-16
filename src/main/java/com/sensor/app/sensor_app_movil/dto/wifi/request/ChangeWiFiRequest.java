package com.sensor.app.sensor_app_movil.dto.wifi.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeWiFiRequest {

    @NotBlank(message = "El codigo del dispositivo no puede ser nulo ni vacio")
    private String deviceCode;

    @NotBlank(message = "El ssid no puede ser nulo ni vacio")
    private String ssid;

    @NotBlank(message = "La contraseña no puede ser nulo ni vacio")
    private String password;

}
