package com.sensor.app.sensor_app_movil.security.controller;

import com.sensor.app.sensor_app_movil.security.jwt.dto.JwtDto;
import com.sensor.app.sensor_app_movil.security.dto.userDTO.LoginUser;
import com.sensor.app.sensor_app_movil.security.dto.userDTO.NewUser;
import com.sensor.app.sensor_app_movil.security.entity.User;
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

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid NewUser newUser) {
        User user = new User();
        user.setName(newUser.getName());
        user.setLastname(newUser.getLastname());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.setDateOfBirth(ConvertStringToCalendar.getCalendar(newUser.getDateOfBirth()));
        user.setNationality(newUser.getNationality());
        authService.register(user);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody LoginUser loginUser) {
        User user = new User();
        user.setEmail(loginUser.getEmail());
        user.setPassword(loginUser.getPassword());
        String jwt = this.authService.login(user);
        return new ResponseEntity<>(new JwtDto(jwt), HttpStatus.OK);

    }

}
