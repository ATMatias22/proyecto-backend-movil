package com.sensor.app.sensor_app_movil.security.dto.userdto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConfirmChangeUserPasswordRequest {

    @NotBlank(message = "El token no puede ser nulo ni vacio")
    private String token;
}
