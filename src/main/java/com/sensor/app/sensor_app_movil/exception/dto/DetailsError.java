package com.sensor.app.sensor_app_movil.exception.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DetailsError {

    private Date time;
    private String message;
    private String details;
    private boolean error = true;
    private int httpStatus;

    public DetailsError(Date time, String message, String details, int httpStatus) {
        super();
        this.time = time;
        this.message = message;
        this.details = details;
        this.httpStatus = httpStatus;
    }


}
