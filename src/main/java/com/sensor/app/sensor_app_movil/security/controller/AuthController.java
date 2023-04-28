package com.sensor.app.sensor_app_movil.security.controller;

import com.sensor.app.sensor_app_movil.security.dto.userDTO.ConfirmRegisterUser;
import com.sensor.app.sensor_app_movil.security.jwt.dto.JwtDto;
import com.sensor.app.sensor_app_movil.security.dto.userDTO.LoginUser;
import com.sensor.app.sensor_app_movil.security.dto.userDTO.NewUser;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.mappers.UserMapper;
import com.sensor.app.sensor_app_movil.security.service.IAuthService;
import com.sensor.app.sensor_app_movil.utils.date.ConvertStringToCalendar;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private IAuthService authService;

    @Autowired
    private UserMapper userMapper;


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid NewUser newUser) {
        authService.register(this.userMapper.newUserRequestToUserEntity(newUser));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody LoginUser loginUser) {
        String jwt = this.authService.login(this.userMapper.loginUserRequestToUserEntity(loginUser));
        return new ResponseEntity<>(new JwtDto(jwt), HttpStatus.OK);
    }


    @PostMapping(path = "/confirm")
    public ResponseEntity<JwtDto> confirm(@RequestBody ConfirmRegisterUser cru) {
        return new ResponseEntity<>(new JwtDto(authService.confirmToken(cru.getToken())), HttpStatus.OK);
    }





}
