package com.sensor.app.sensor_app_movil.dto.device.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddDeviceFromWebAdminRequest {


    @Size(max = 90, message = "El codigo de dispositivo no debe ser mayor a 90")
    @NotBlank(message = "El codigo de dispositivo no puede ser nulo ni vacio")
    private String deviceCode;

    @Size(max = 75, message = "La contraseña del dispositivo no debe ser mayor a 75")
    @NotBlank(message = "La contraseña del dispositivo no puede ser nulo ni vacio")
    private String password;
}
