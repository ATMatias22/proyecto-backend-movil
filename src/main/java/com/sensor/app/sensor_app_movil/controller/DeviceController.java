package com.sensor.app.sensor_app_movil.controller;


import com.sensor.app.sensor_app_movil.dto.device.AddObserverRequest;
import com.sensor.app.sensor_app_movil.dto.device.LinkDeviceRequest;
import com.sensor.app.sensor_app_movil.dto.device.OwnDevicesResponse;
import com.sensor.app.sensor_app_movil.mappers.DeviceMapper;
import com.sensor.app.sensor_app_movil.service.IDeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
@CrossOrigin(origins = "*")
public class DeviceController {



    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private DeviceMapper deviceMapper;

    @GetMapping("/own")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OwnDevicesResponse>> getDeviceOwn(@RequestParam(name = "page", defaultValue = "0") int page) {
       List<OwnDevicesResponse> odr = this.deviceService.getAllByFkUser(page).stream().map(device -> deviceMapper.toOwnDevicesResponse(device)).toList();
        return new ResponseEntity<>(odr, HttpStatus.OK);
    }

    @PutMapping("/link-user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> linkUser(@RequestBody @Valid LinkDeviceRequest ldr) {
        this.deviceService.linkUser(ldr.getCode(), ldr.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/add-observer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> addObserver(@RequestBody @Valid AddObserverRequest addObserverRequest) {

        this.deviceService.addObserver(addObserverRequest.getEmail(), addObserverRequest.getDeviceCode());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/confirm-invitation")
    public ResponseEntity<Void> confirmInvitation(@RequestParam("token") String token) {
        this.deviceService.confirmationInvitation(token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{deviceCode}/unlink-observer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void>  unlinkObserver(@PathVariable("deviceCode") String deviceCode ) {
        this.deviceService.unlinkObserver(deviceCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{deviceCode}/user/{email}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void>  deleteObserver(@PathVariable("deviceCode") String deviceCode,@PathVariable("email") String email ) {
        this.deviceService.deleteObserver(deviceCode, email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/{deviceCode}/clear-history")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> clearHistory(@PathVariable("deviceCode") String deviceCode ) {
        this.deviceService.clearHistory(deviceCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/{deviceCode}/delete-owner")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void>  deleteDeviceFromUser(@PathVariable("deviceCode") String deviceCode ) {
        this.deviceService.deleteDeviceFromUser(deviceCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
