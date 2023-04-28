package com.sensor.app.sensor_app_movil.security.controller;

import com.sensor.app.sensor_app_movil.security.dto.userDTO.*;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.jwt.dto.JwtDto;
import com.sensor.app.sensor_app_movil.security.mappers.UserMapper;
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

    @Autowired
    private UserMapper userMapper;


    @PutMapping("/modify-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity modifyPassword(@RequestBody @Valid ModifyPasswordRequest mpr) {
        this.userService.modifyPassword(mpr.getPassword(), mpr.getNewPassword());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/confirm-password")
    public ResponseEntity confirmPassword(@RequestParam("token") String token) {
        this.userService.confirmTokenPasswordChange(token);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserLoggedInResponse> getUserLoggedIn() {
        UserLoggedInResponse ulr = this.userMapper.userEntityToUserLoggedInResponse(this.userService.getUserLoggedIn());
        return new ResponseEntity<>(ulr, HttpStatus.OK);
    }

    @PutMapping("/modify-data")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity modifyData(@RequestBody @Valid ModifyDataRequest mdr) {
        this.userService.modifyData(this.userMapper.modifyDataRequestToUserEntity(mdr));
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }


    @GetMapping(path = "/confirm-data")
    public ResponseEntity confirm(@RequestParam("token") String token) {
        this.userService.confirmTokenEmailChange(token);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/delete-user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity confirm(@RequestBody DeleteUser du) {
        this.userService.deleteUser(du.getPassword());
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }





}
