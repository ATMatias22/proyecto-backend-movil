package com.sensor.app.sensor_app_movil.dto.device.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConfirmChangeDevicePasswordRequest {

    @NotBlank(message = "El token no puede ser nulo ni vacio")
    private String token;

}
