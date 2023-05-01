package com.sensor.app.sensor_app_movil.security.jwt.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtDto {

    private String token;
    private String bearer = "Bearer";

    public JwtDto(String token) {
        this.token = token;
    }
}
