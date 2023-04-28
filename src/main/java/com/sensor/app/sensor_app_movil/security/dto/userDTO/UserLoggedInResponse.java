package com.sensor.app.sensor_app_movil.security.dto.userDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoggedInResponse {

    private String name;

    private String lastname;

    private String email;

    private String dateOfBirth;

    private String nationality;

}
