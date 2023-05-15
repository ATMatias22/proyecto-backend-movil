package com.sensor.app.sensor_app_movil.dto.informativemessage.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InformativeMessageResponse {

    private String message;

    private LocalDateTime created;

}
