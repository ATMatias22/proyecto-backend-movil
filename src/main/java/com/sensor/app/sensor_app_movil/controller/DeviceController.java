package com.sensor.app.sensor_app_movil.controller;


import com.sensor.app.sensor_app_movil.dto.device.request.*;
import com.sensor.app.sensor_app_movil.dto.device.response.ObservedDeviceResponse;
import com.sensor.app.sensor_app_movil.dto.device.response.OwnDeviceResponse;
import com.sensor.app.sensor_app_movil.dto.device.response.OwnDevicesResponse;
import com.sensor.app.sensor_app_movil.dto.device.response.DeviceStatusResponse;
import com.sensor.app.sensor_app_movil.dto.informativemessage.response.InformativeMessageResponse;
import com.sensor.app.sensor_app_movil.dto.observer.response.ObserverResponse;
import com.sensor.app.sensor_app_movil.dto.wifi.request.ChangeWiFiRequest;
import com.sensor.app.sensor_app_movil.mappers.DeviceMapper;
import com.sensor.app.sensor_app_movil.mappers.InformativeMessageMapper;
import com.sensor.app.sensor_app_movil.mappers.ObserverMapper;
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
    @Autowired
    private InformativeMessageMapper informativeMessageMapper;

    @Autowired
    private ObserverMapper observerMapper;

    @GetMapping(path = "/own", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OwnDevicesResponse>> getDeviceOwn(@RequestParam(name = "page", defaultValue = "0") int page) {
       List<OwnDevicesResponse> odr = this.deviceService.getAllByFkUser(page);
        return new ResponseEntity<>(odr, HttpStatus.OK);
    }

    @GetMapping(path = "/observed", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ObservedDeviceResponse>> getDeviceObserved(@RequestParam(name = "page", defaultValue = "0") int page) {
        List<ObservedDeviceResponse> odr = this.deviceService.getAllByObserver(page);
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
        OwnDeviceResponse odr = this.deviceService.getByDeviceCodeForOwner(deviceCode);
        return new ResponseEntity<>(odr,HttpStatus.OK);
    }

    @PutMapping(path ="/change-name", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changeNameDevice(@RequestBody @Valid ChangeNameRequest changeNameRequest) {
        this.deviceService.changeDeviceName(changeNameRequest.getDeviceCode(), changeNameRequest.getNewName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping(path ="/change-device-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changeDevicePassword(@RequestBody @Valid ChangeDevicePasswordRequest changeDevicePasswordRequest) {
        this.deviceService.changeDevicePassword(changeDevicePasswordRequest.getDeviceCode(), changeDevicePasswordRequest.getOldPassword(), changeDevicePasswordRequest.getNewPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/confirm-change-device-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> confirmChangeDevicePassword(@RequestBody @Valid ConfirmChangeDevicePasswordRequest confirmChangeDevicePasswordRequest) {
        this.deviceService.confirmTokenChangeDevicePassword(confirmChangeDevicePasswordRequest.getToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{deviceCode}/history", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<InformativeMessageResponse>> getHistory(@PathVariable("deviceCode") String deviceCode ) {
        List<InformativeMessageResponse> history = this.deviceService.getHistory(deviceCode).stream().map( informativeMessage -> this.informativeMessageMapper.toInformativeMessageResponse(informativeMessage)).toList();
        return new ResponseEntity<>(history,HttpStatus.OK);
    }


    @GetMapping(path = "/{deviceCode}/observer", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ObserverResponse>> getObserversOnTheDevice(@PathVariable("deviceCode") String deviceCode ) {
        List<ObserverResponse> observers = this.deviceService.getObserversOnTheDevice(deviceCode).stream().map( observer -> this.observerMapper.toObserverResponse(observer)).toList();
        return new ResponseEntity<>(observers,HttpStatus.OK);
    }

    @PutMapping(path ="/change-device-wifi", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changeDeviceWiFi(@RequestBody @Valid ChangeWiFiRequest changeWiFiRequest) {
        this.deviceService.changeWiFi(changeWiFiRequest.getDeviceCode(), changeWiFiRequest.getSsid(), changeWiFiRequest.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path ="/turn-on", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DeviceStatusResponse> turnOnDevice(@RequestBody @Valid DeviceStatusRequest deviceStatusRequest) {
        DeviceStatusResponse todr = this.deviceService.turnOnDevice(deviceStatusRequest.getDeviceCode());
        return new ResponseEntity<>(todr,HttpStatus.OK);
    }

    @PostMapping(path ="/turn-off", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DeviceStatusResponse> turnOffDevice(@RequestBody @Valid DeviceStatusRequest deviceStatusRequest) {
        DeviceStatusResponse todr = this.deviceService.turnOffDevice(deviceStatusRequest.getDeviceCode());
        return new ResponseEntity<>(todr,HttpStatus.OK);
    }
}
