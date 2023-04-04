package com.sensor.app.sensor_app_movil.security.controller;

import com.sensor.app.sensor_app_movil.security.dto.userDTO.ModifyPasswordRequest;
import com.sensor.app.sensor_app_movil.security.dto.userDTO.NewUser;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.service.IAuthService;
import com.sensor.app.sensor_app_movil.security.service.IUserService;
import com.sensor.app.sensor_app_movil.utils.date.ConvertStringToCalendar;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;


    @PutMapping("/modify-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity modifyPassword(@RequestBody @Valid ModifyPasswordRequest mpr) {
        this.userService.modifyPassword(mpr.getPassword(), mpr.getNewPassword());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
