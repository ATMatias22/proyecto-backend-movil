package com.sensor.app.sensor_app_movil.security.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeleteUserRequest {

    @NotBlank(message = "La contraseña no puede ser nulo ni vacio")
    private String password;

}
