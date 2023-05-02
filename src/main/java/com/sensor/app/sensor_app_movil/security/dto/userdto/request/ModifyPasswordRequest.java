package com.sensor.app.sensor_app_movil.security.dto.userdto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyPasswordRequest {


    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{8,}$", message = " La password debe tener un mínimo de 8 caracteres, de los cuales debe haber al menos una mayúscula y un número")
    private String password;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Z]).{8,}$", message = " La nueva password debe tener un mínimo de 8 caracteres, de los cuales debe haber al menos una mayúscula y un número")
    private String newPassword;

}
