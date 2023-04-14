package com.sensor.app.sensor_app_movil.dto.device;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BindDeviceRequest {


    @NotBlank(message = "El codigo no puede ser nulo ni vacio")
    private String code;

    @NotBlank(message = "La password no puede ser nulo ni vacio")
    private String password;

}
