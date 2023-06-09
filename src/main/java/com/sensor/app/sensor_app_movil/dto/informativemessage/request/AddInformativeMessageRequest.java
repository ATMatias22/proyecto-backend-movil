package com.sensor.app.sensor_app_movil.dto.informativemessage.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddInformativeMessageRequest {

    @NotBlank(message = "El codigo de dispositivo no puede ser nulo ni vacio")
    private String deviceCode;

    @NotBlank(message = "El mensaje no puede ser nulo ni vacio")
    private String message;

    @NotBlank(message = "El token no puede ser nulo ni vacio")
    private String token;
}
