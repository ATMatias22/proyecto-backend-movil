package com.sensor.app.sensor_app_movil.dto.device.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeNameRequest {

    @NotBlank(message = "El codigo de dispostiivo no puede ser nulo ni vacio")
    @Size(message = "El codigo del dispositivo no puede superar los 90 caracteres", max = 90)
    private String deviceCode;


    @NotNull(message = "El nombre del dispositivo no puede ser vacio")
    @Size(message = "El nombre del dispositivo no puede superar los 90 caracteres", max = 90)
    private String newName;


}
