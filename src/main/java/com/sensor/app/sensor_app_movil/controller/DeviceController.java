package com.sensor.app.sensor_app_movil.controller;


import com.sensor.app.sensor_app_movil.dto.device.AddObserverRequest;
import com.sensor.app.sensor_app_movil.dto.device.BindDeviceRequest;
import com.sensor.app.sensor_app_movil.security.dto.userDTO.NewUser;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.service.IDeviceService;
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
@RequestMapping("/device")
@CrossOrigin(origins = "*")
public class DeviceController {


    private final static Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private IDeviceService deviceService;


    @PutMapping("/bind")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity bindUser(@RequestBody @Valid  BindDeviceRequest bdr) {
        this.deviceService.bindUser(bdr.getCode(), bdr.getPassword());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/add-observer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity addObserver(@RequestBody @Valid AddObserverRequest addObserverRequest) {

        this.deviceService.addObserver(addObserverRequest.getEmail(), addObserverRequest.getDeviceCode());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/confirm-invitation")
    public ResponseEntity confirmInvitation(@RequestParam("token") String token) {
        this.deviceService.confirmationInvitation(token);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
