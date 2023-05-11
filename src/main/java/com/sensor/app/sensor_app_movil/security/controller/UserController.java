package com.sensor.app.sensor_app_movil.security.controller;

import com.sensor.app.sensor_app_movil.security.dto.userdto.request.ConfirmChangeUserPasswordRequest;
import com.sensor.app.sensor_app_movil.security.dto.userdto.request.DeleteUserRequest;
import com.sensor.app.sensor_app_movil.security.dto.userdto.request.ModifyDataRequest;
import com.sensor.app.sensor_app_movil.security.dto.userdto.request.ModifyPasswordRequest;
import com.sensor.app.sensor_app_movil.security.dto.userdto.response.UserLoggedInResponse;
import com.sensor.app.sensor_app_movil.security.mappers.UserMapper;
import com.sensor.app.sensor_app_movil.security.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {


    @Autowired
    private IUserService userService;

    @Autowired
    private UserMapper userMapper;


    @PutMapping(path ="/modify-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> modifyPassword(@RequestBody @Valid ModifyPasswordRequest mpr) {
        this.userService.modifyPassword(mpr.getPassword(), mpr.getNewPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/confirm-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> confirmPassword(@RequestBody @Valid ConfirmChangeUserPasswordRequest confirmChangeUserPasswordRequest) {
        this.userService.confirmTokenPasswordChange(confirmChangeUserPasswordRequest.getToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserLoggedInResponse> getUserLoggedIn() {
        UserLoggedInResponse ulr = this.userMapper.userEntityToUserLoggedInResponse(this.userService.getUserLoggedIn());
        return new ResponseEntity<>(ulr, HttpStatus.OK);
    }

    @PutMapping(path = "/modify-data", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> modifyData(@RequestBody @Valid ModifyDataRequest mdr) {
        this.userService.modifyData(this.userMapper.modifyDataRequestToUserEntity(mdr));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


    @GetMapping(path = "/confirm-data")
    public ResponseEntity<Void> confirm(@RequestParam("token") String token) {
        this.userService.confirmTokenEmailChange(token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(path = "/delete-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> confirm(@RequestBody DeleteUserRequest du) {
        this.userService.deleteUser(du.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }





}
