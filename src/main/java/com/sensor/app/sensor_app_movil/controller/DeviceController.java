package com.sensor.app.sensor_app_movil.controller;


import com.sensor.app.sensor_app_movil.dto.device.AddObserverRequest;
import com.sensor.app.sensor_app_movil.dto.device.LinkDeviceRequest;
import com.sensor.app.sensor_app_movil.dto.device.OwnDevicesResponse;
import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.mappers.DeviceMapper;
import com.sensor.app.sensor_app_movil.service.IDeviceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/device")
@CrossOrigin(origins = "*")
public class DeviceController {


    private final static Logger logger = LoggerFactory.getLogger(DeviceController.class);

    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private DeviceMapper deviceMapper;

    @GetMapping("/own")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OwnDevicesResponse>> getDeviceOwn(@RequestParam(name = "page", defaultValue = "0") int page) {
       List<OwnDevicesResponse> odr = this.deviceService.getAllByFkUser(page).stream().map(device -> deviceMapper.toOwnDevicesResponse(device)).collect(Collectors.toList());
        return new ResponseEntity<>(odr, HttpStatus.OK);
    }

    @PutMapping("/link-user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity linkUser(@RequestBody @Valid LinkDeviceRequest ldr) {
        this.deviceService.linkUser(ldr.getCode(), ldr.getPassword());
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

    @DeleteMapping("/{deviceCode}/unlink-observer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity unlinkObserver(@PathVariable("deviceCode") String deviceCode ) {
        this.deviceService.unlinkObserver(deviceCode);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{deviceCode}/user/{email}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity unlinkObserver(@PathVariable("deviceCode") String deviceCode,@PathVariable("email") String email ) {
        this.deviceService.deleteObserver(deviceCode, email);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/{deviceCode}/clear-history")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity clearHistory(@PathVariable("deviceCode") String deviceCode ) {
        this.deviceService.clearHistory(deviceCode);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/{deviceCode}/delete-owner")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity deleteDeviceFromUser(@PathVariable("deviceCode") String deviceCode ) {
        this.deviceService.deleteDeviceFromUser(deviceCode);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
