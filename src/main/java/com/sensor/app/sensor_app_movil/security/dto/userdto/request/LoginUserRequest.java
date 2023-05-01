package com.sensor.app.sensor_app_movil.security.dto.userdto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginUserRequest {
    private String email;
    private String password;
}
