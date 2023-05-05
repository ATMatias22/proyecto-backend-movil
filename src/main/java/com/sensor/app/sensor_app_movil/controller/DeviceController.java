package com.sensor.app.sensor_app_movil.controller;


import com.sensor.app.sensor_app_movil.dto.device.request.AddObserverRequest;
import com.sensor.app.sensor_app_movil.dto.device.request.ChangeNameRequest;
import com.sensor.app.sensor_app_movil.dto.device.request.LinkDeviceRequest;
import com.sensor.app.sensor_app_movil.dto.device.response.ObservedDeviceResponse;
import com.sensor.app.sensor_app_movil.dto.device.response.OwnDeviceResponse;
import com.sensor.app.sensor_app_movil.dto.device.response.OwnDevicesResponse;
import com.sensor.app.sensor_app_movil.mappers.DeviceMapper;
import com.sensor.app.sensor_app_movil.service.IDeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @GetMapping(path = "/own", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OwnDevicesResponse>> getDeviceOwn(@RequestParam(name = "page", defaultValue = "0") int page) {
       List<OwnDevicesResponse> odr = this.deviceService.getAllByFkUser(page).stream().map(device -> deviceMapper.toOwnDevicesResponse(device)).toList();
        return new ResponseEntity<>(odr, HttpStatus.OK);
    }

    @GetMapping(path = "/observed", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ObservedDeviceResponse>> getDeviceObserved(@RequestParam(name = "page", defaultValue = "0") int page) {
        List<ObservedDeviceResponse> odr = this.deviceService.getAllByObserver(page).stream().map(device -> deviceMapper.toObservedDeviceResponse(device)).toList();
        return new ResponseEntity<>(odr, HttpStatus.OK);
    }

    @PutMapping(path ="/link-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> linkUser(@RequestBody @Valid LinkDeviceRequest ldr) {
        this.deviceService.linkUser(ldr.getCode(), ldr.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(path ="/add-observer", consumes = MediaType.APPLICATION_JSON_VALUE)
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

    @DeleteMapping(path ="/{deviceCode}/unlink-observer")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void>  unlinkObserver(@PathVariable("deviceCode") String deviceCode ) {
        this.deviceService.unlinkObserver(deviceCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path ="/{deviceCode}/user/{email}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void>  deleteObserver(@PathVariable("deviceCode") String deviceCode,@PathVariable("email") String email ) {
        this.deviceService.deleteObserver(deviceCode, email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping(path = "/{deviceCode}/clear-history")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> clearHistory(@PathVariable("deviceCode") String deviceCode ) {
        this.deviceService.clearHistory(deviceCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping(path = "/{deviceCode}/delete-owner")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void>  deleteDeviceFromUser(@PathVariable("deviceCode") String deviceCode ) {
        this.deviceService.deleteDeviceFromUser(deviceCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping(path = "/{deviceCode}/own", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OwnDeviceResponse>  getByDeviceCodeForOwner(@PathVariable("deviceCode") String deviceCode ) {
        OwnDeviceResponse device = this.deviceMapper.toOwnDeviceResponse(this.deviceService.getByDeviceCodeForOwner(deviceCode));
        System.out.println(device);
        return new ResponseEntity<>(device,HttpStatus.OK);
    }

    @PutMapping(path ="/change-name", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changeNameDevice(@RequestBody @Valid ChangeNameRequest changeNameRequest) {
        this.deviceService.changeDeviceName(changeNameRequest.getDeviceCode(), changeNameRequest.getNewName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
