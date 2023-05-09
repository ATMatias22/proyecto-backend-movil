package com.sensor.app.sensor_app_movil.dto.device.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeDevicePasswordRequest {

    @NotBlank(message = "El codigo de dispositivo no puede ser nulo ni vacio")
    @Size(message = "El codigo del dispositivo no puede superar los 90 caracteres", max = 90)
    String deviceCode;

    @NotBlank(message = "La contraseña antigua no puede ser nulo ni vacio")
    String oldPassword;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{8,}$", message = " La nueva password debe tener un mínimo de 8 caracteres, de los cuales debe haber al menos una mayúscula y un número")
    @NotNull(message = "La nueva password no puede ser nulo")
    String newPassword;

}
