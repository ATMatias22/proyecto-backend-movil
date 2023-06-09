package com.sensor.app.sensor_app_movil.controller;


import com.sensor.app.sensor_app_movil.dto.informativemessage.request.AddInformativeMessageRequest;
import com.sensor.app.sensor_app_movil.service.IDeviceService;
import com.sensor.app.sensor_app_movil.service.IInformativeMessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/arduino")
@CrossOrigin(origins = "*")
public class ArduinoController {

    @Autowired
    private IInformativeMessageService informativeMessageService;


    @PostMapping(path ="/add-informative-message", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addInformativeMessage(@RequestBody @Valid AddInformativeMessageRequest addInformativeMessageRequest) {

        this.informativeMessageService.save(addInformativeMessageRequest.getDeviceCode(), addInformativeMessageRequest.getMessage(), addInformativeMessageRequest.getToken());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
