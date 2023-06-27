package com.sensor.app.sensor_app_movil.security.dto.user.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConfirmRegisterUserRequest {
    private String token;
}
