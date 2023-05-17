package com.sensor.app.sensor_app_movil.dto.observer.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObserverResponse {

    private String name;
    private String lastname;
    private String email;
    private String created;

}
