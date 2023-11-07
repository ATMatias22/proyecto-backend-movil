package com.sensor.app.sensor_app_movil.exception;

import org.springframework.http.HttpStatus;

public class JwtAppWebException extends RuntimeException{

    private static final long serialVersionUID = 1L;


    public JwtAppWebException(String message) {
        super(message);
    }


}
