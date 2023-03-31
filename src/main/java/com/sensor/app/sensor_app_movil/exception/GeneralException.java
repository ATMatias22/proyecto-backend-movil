package com.sensor.app.sensor_app_movil.exception;

import org.springframework.http.HttpStatus;

public class GeneralException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private HttpStatus status;

    public GeneralException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }


}
