package com.sensor.app.sensor_app_movil.security.controller;

import com.sensor.app.sensor_app_movil.security.dto.user.request.ConfirmRegisterUserRequest;
import com.sensor.app.sensor_app_movil.security.jwt.dto.JwtDto;
import com.sensor.app.sensor_app_movil.security.dto.user.request.LoginUserRequest;
import com.sensor.app.sensor_app_movil.security.dto.user.request.NewUserRequest;
import com.sensor.app.sensor_app_movil.security.mappers.UserMapper;
import com.sensor.app.sensor_app_movil.security.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @Autowired
    private UserMapper userMapper;


    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void>  register(@RequestBody @Valid NewUserRequest newUser) {
        authService.register(this.userMapper.newUserRequestToUserEntity(newUser));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtDto> login(@RequestBody LoginUserRequest loginUser) {
        String jwt = this.authService.login(this.userMapper.loginUserRequestToUserEntity(loginUser));
        return new ResponseEntity<>(new JwtDto(jwt), HttpStatus.OK);
    }


    @PostMapping(path = "/confirm", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtDto> confirm(@RequestBody ConfirmRegisterUserRequest cru) {
        return new ResponseEntity<>(new JwtDto(authService.confirmToken(cru.getToken())), HttpStatus.OK);
    }





}
