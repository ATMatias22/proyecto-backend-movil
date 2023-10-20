package com.sensor.app.sensor_app_movil.service.implementation;

import com.sensor.app.sensor_app_movil.dto.arduino.response.ArduinoDeviceStatusResponse;
import com.sensor.app.sensor_app_movil.dto.arduino.response.DeviceWiFiStatusResponse;
import com.sensor.app.sensor_app_movil.dto.arduino.response.SaveWifiResponse;
import com.sensor.app.sensor_app_movil.dto.device.response.ObservedDeviceResponse;
import com.sensor.app.sensor_app_movil.dto.device.response.OwnDeviceResponse;
import com.sensor.app.sensor_app_movil.dto.device.response.OwnDevicesResponse;
import com.sensor.app.sensor_app_movil.dto.device.response.DeviceStatusResponse;
import com.sensor.app.sensor_app_movil.entity.*;
import com.sensor.app.sensor_app_movil.entity.Observer;
import com.sensor.app.sensor_app_movil.exception.GeneralException;
import com.sensor.app.sensor_app_movil.mappers.DeviceMapper;
import com.sensor.app.sensor_app_movil.dao.IDeviceDao;
import com.sensor.app.sensor_app_movil.security.MainUser;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.service.*;
import com.sensor.app.sensor_app_movil.security.service.IEmailService;
import com.sensor.app.sensor_app_movil.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class DeviceServiceImpl implements IDeviceService {

    @Autowired
    private IDeviceDao deviceDao;
    @Autowired
    private IUserService userService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IObserverService observerService;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private IConfirmationTokenInvitationService confirmationTokenInvitationService;
    @Autowired
    private IInformativeMessageService informativeMessageService;

    @Autowired
    private IConfirmationTokenDevicePasswordChangeService confirmationTokenDevicePasswordChangeService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DeviceMapper deviceMapper;

    private static final String DEVICE_WITHOUT_OWNER_MESSAGE = "Este dispositivo no tiene dueño";


    public DeviceServiceImpl(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void linkUser(String deviceCode, String password) {

        Device device = this.getByDeviceCode(deviceCode);
        if (!passwordEncoder.matches(password, device.getPassword())) {
            throw new GeneralException(HttpStatus.BAD_REQUEST, "Alguna de las credenciales del dispositivo es incorrecta");
        }

        if (device.getFkUser() != null) {
            throw new GeneralException(HttpStatus.BAD_REQUEST, "El dispositivo en el que se quiere vincular, ya fue vinculado a otra persona");
        }

        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.getUserByEmail(mu.getUsername());
        device.setFkUser(user);
        this.deviceDao.save(device);
    }

    @Override
    public OwnDeviceResponse getByDeviceCodeForOwner(String deviceCode) {
        Device device = this.getByDeviceCode(deviceCode);
        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (device.getFkUser() == null) {
            throw new GeneralException(HttpStatus.BAD_REQUEST, "El dispositivo no tiene dueño");
        } else {
            if (device.getFkUser().getIdUser() != mu.getId()) {
                throw new GeneralException(HttpStatus.UNAUTHORIZED, "No puede acceder a este dispositivo");
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "hola");
        HttpEntity<ArduinoDeviceStatusResponse> requestEntityDeviceStatus = new HttpEntity<>(headers);
        HttpEntity<DeviceWiFiStatusResponse> requestEntityDeviceWiFiStatus = new HttpEntity<>(headers);

        ResponseEntity<ArduinoDeviceStatusResponse> responseDeviceStatus = null;
        ResponseEntity<DeviceWiFiStatusResponse> responseDeviceWiFiStatus = null;
        try {
            responseDeviceStatus = restTemplate.exchange("http://192.168.0.254:80/verificar-estado-sensor", HttpMethod.GET, requestEntityDeviceStatus, new ParameterizedTypeReference<ArduinoDeviceStatusResponse>() {
            });
            responseDeviceWiFiStatus = restTemplate.exchange("http://192.168.0.254:80/verificar-wifi", HttpMethod.GET, requestEntityDeviceWiFiStatus, new ParameterizedTypeReference<DeviceWiFiStatusResponse>() {
            });
        } catch (HttpClientErrorException.NotFound enf) {
            System.out.println(enf.getMessage());
            return this.deviceMapper.toOwnDeviceResponse(device, null, null, "Arduino no encontro el recurso");
        } catch (HttpClientErrorException.Forbidden efb) {
            System.out.println(efb.getMessage());
            return this.deviceMapper.toOwnDeviceResponse(device, null, null, "Arduino no te permite el acceso");
        } catch (ResourceAccessException rae) {
            //ATRAPA ERROR 5XX
            System.out.println(rae.getMessage());
            return this.deviceMapper.toOwnDeviceResponse(device, null, null, "No se pudo establecer conexion con el arduino, probablemente no este conectado a una fuente de energia o no tenga WiFi conectado");
        }

        ArduinoDeviceStatusResponse arduinoDeviceStatusResponse = responseDeviceStatus.getBody();

        // Obtener el mensaje y el estado de la respuesta
        String messageDeviceStatus = arduinoDeviceStatusResponse.getMessage();
        Boolean statusDeviceStatus = arduinoDeviceStatusResponse.getOn();

        // Utilizar los datos obtenidos según sea necesario
        System.out.println("Mensaje estado device: " + messageDeviceStatus);
        System.out.println("Estado estado device: " + statusDeviceStatus);

        DeviceWiFiStatusResponse deviceWiFiStatusResponse = responseDeviceWiFiStatus.getBody();

        // Obtener el mensaje y el estado de la respuesta
        String messageDeviceWiFiStatus = deviceWiFiStatusResponse.getMessage();
        Boolean statusDeviceWiFiStatus = deviceWiFiStatusResponse.getOn();

        // Utilizar los datos obtenidos según sea necesario
        System.out.println("Mensaje estado wifi: " + messageDeviceWiFiStatus);
        System.out.println("Estado estado wifi: " + statusDeviceWiFiStatus);

        return this.deviceMapper.toOwnDeviceResponse(device, statusDeviceStatus, statusDeviceWiFiStatus, "Se pudo establecer conexion con el arduino correctamente");

    }


    @Transactional
    @Override
    public void addObserver(String email, String deviceCode) {

        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = this.userService.getUserByEmail(email);

        Device device = this.getByDeviceCode(deviceCode);

        if (device.getFkUser() == null) {
            throw new GeneralException(HttpStatus.UNAUTHORIZED, "No puede invitar a un usuario si no es el dueño del dispositivo");
        }

        if (device.getFkUser().getIdUser() != mu.getId()) {
            throw new GeneralException(HttpStatus.UNAUTHORIZED, "No puede invitar a un usuario si no es el dueño del dispositivo");
        }

        if (this.observerService.existsByUserAndDevice(user, device)) {
            throw new GeneralException(HttpStatus.BAD_REQUEST, "El usuario con email: " + user.getEmail() + " ya existe como invitado en tu dispositivo");
        }

        ConfirmationTokenInvitation cti = null;
        String token = UUID.randomUUID().toString();

        try {
            cti = this.confirmationTokenInvitationService.getByUserAndDevice(user, device);
            cti.setToken(token);
            cti.setCreatedAt(LocalDateTime.now());
            cti.setExpiredAt(LocalDateTime.now().plusDays(2));
            this.confirmationTokenInvitationService.saveConfirmationTokenInvitation(cti);
        } catch (GeneralException ge) {
            cti = new ConfirmationTokenInvitation(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(2),
                    user,
                    device
            );
            this.confirmationTokenInvitationService.saveConfirmationTokenInvitation(cti);
        }
        String link = "http://localhost:8081/app_movil_sensor/api/device/confirm-invitation?token=" + token;
        emailService.send("Invitacion al dispositivo", email, buildEmailForConfirmInvitation(link, mu.getUsername()));


    }

    @Override
    public void unlinkObserver(String deviceCode) {
        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.getUserByEmail(mu.getUsername());
        Device device = this.getByDeviceCode(deviceCode);
        Observer observer = this.observerService.getObserverByUserAndDevice(user, device);
        this.observerService.delete(observer);
    }

    @Transactional
    @Override
    public void confirmationInvitation(String token) {

        ConfirmationTokenInvitation confirmationToken = this.confirmationTokenInvitationService
                .getByToken(token);

        Observer obs = new Observer();
        obs.setFkDevice(confirmationToken.getFkDevice());
        obs.setFkUser(confirmationToken.getFkUser());

        this.observerService.save(obs);
        this.confirmationTokenInvitationService.deleteByToken(token);
    }

    @Override
    public void deleteObserver(String deviceCode, String deletedUserEmail) {
        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Device device = this.getByDeviceCode(deviceCode);
        User userDeleted = this.userService.getUserByEmail(deletedUserEmail);

        if (device.getFkUser() != null) {
            if (device.getFkUser().getIdUser() != mu.getId()) {
                throw new GeneralException(HttpStatus.UNAUTHORIZED, "No puede eliminar a un invitado si no es el dueño del dispositivo");
            }
        } else {
            throw new GeneralException(HttpStatus.UNAUTHORIZED, DEVICE_WITHOUT_OWNER_MESSAGE);
        }

        Observer observer = this.observerService.getObserverByUserAndDevice(userDeleted, device);
        this.observerService.delete(observer);
    }

    @Transactional
    @Override
    public void clearHistory(String deviceCode) {
        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Device device = this.getByDeviceCode(deviceCode);
        if (device.getFkUser() != null) {
            if (device.getFkUser().getIdUser() != mu.getId()) {
                throw new GeneralException(HttpStatus.UNAUTHORIZED, "No puede vaciar el chat de un invitado si no es el dueño del dispositivo");
            }
        } else {
            throw new GeneralException(HttpStatus.UNAUTHORIZED, DEVICE_WITHOUT_OWNER_MESSAGE);
        }

        this.informativeMessageService.deleteByFkDevice(device);

    }

    @Transactional
    @Override
    public void deleteDeviceFromUser(String deviceCode) {
        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Device device = this.getByDeviceCode(deviceCode);
        if (device.getFkUser() != null) {
            if (device.getFkUser().getIdUser() != mu.getId()) {
                throw new GeneralException(HttpStatus.UNAUTHORIZED, "No puede vaciar el chat de un invitado si no es el dueño del dispositivo");
            }
        } else {
            throw new GeneralException(HttpStatus.UNAUTHORIZED, DEVICE_WITHOUT_OWNER_MESSAGE);
        }

        device.setFkUser(null);
        this.informativeMessageService.deleteByFkDevice(device);
        this.observerService.deleteByFkDevice(device);
        this.deviceDao.save(device);

    }

    @Override
    @Transactional
    public void deleteAllWhenDeleteUser(User user) {

        List<Device> devices = this.deviceDao.getAllByFkUser(user);

        devices.forEach(device -> {
            this.informativeMessageService.deleteByFkDevice(device);
            device.setFkUser(null);
            this.deviceDao.save(device);
            //elimina a los usuarios que pertenecen a un dispositivo donde el usuario a eliminar es dueño
            this.observerService.deleteByFkDevice(device);
            //elimina las confirmaciones que existen sobre ese dispositivo
            this.confirmationTokenInvitationService.deleteByFkDevice(device);
        });

        //elimina todas las solicitudes que tenia para ser invitado de algun dispositivo
        this.confirmationTokenInvitationService.deleteByFkUser(user);

        //elimina todas las solicitudes que tenia para cambiar el password del dispositivo
        this.confirmationTokenDevicePasswordChangeService.deleteByFkUser(user);

        //elimina a todos los usuarios donde es invitado
        this.observerService.deleteAllByFkUser(user);

    }

    @Override
    public List<OwnDevicesResponse> getAllByFkUser(int page) {
        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.getUserByEmail(mu.getUsername());
        Pageable pageable = PageRequest.of(page, 5);
        List<Device> devices = this.deviceDao.getAllByFkUser(user, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "hola");
        HttpEntity<ArduinoDeviceStatusResponse> requestEntityDeviceStatus = new HttpEntity<>(headers);
        HttpEntity<DeviceWiFiStatusResponse> requestEntityDeviceWiFiStatus = new HttpEntity<>(headers);

        return devices.stream().map((device) -> {
            ResponseEntity<ArduinoDeviceStatusResponse> responseDeviceStatus = null;
            ResponseEntity<DeviceWiFiStatusResponse> responseDeviceWiFiStatus = null;
            try {
                responseDeviceStatus = restTemplate.exchange("http://192.168.0.254:80/verificar-estado-sensor", HttpMethod.GET, requestEntityDeviceStatus, new ParameterizedTypeReference<ArduinoDeviceStatusResponse>() {
                });
                responseDeviceWiFiStatus = restTemplate.exchange("http://192.168.0.254:80/verificar-wifi", HttpMethod.GET, requestEntityDeviceWiFiStatus, new ParameterizedTypeReference<DeviceWiFiStatusResponse>() {
                });
            } catch (HttpClientErrorException.NotFound enf) {
                System.out.println(enf.getMessage());
                return this.deviceMapper.toOwnDevicesResponse(device, null, null, "Arduino no encontro el recurso");
            } catch (HttpClientErrorException.Forbidden efb) {
                System.out.println(efb.getMessage());
                return this.deviceMapper.toOwnDevicesResponse(device, null, null, "Arduino no te permite el acceso");
            } catch (ResourceAccessException rae) {
                //ATRAPA ERROR 5XX
                System.out.println(rae.getMessage());
                return this.deviceMapper.toOwnDevicesResponse(device, null, null, "No se pudo establecer conexion con el arduino, probablemente no este conectado a una fuente de energia o no tenga WiFi conectado");
            }

            ArduinoDeviceStatusResponse arduinoDeviceStatusResponse = responseDeviceStatus.getBody();

            // Obtener el mensaje y el estado de la respuesta
            String messageDeviceStatus = arduinoDeviceStatusResponse.getMessage();
            Boolean statusDeviceStatus = arduinoDeviceStatusResponse.getOn();

            // Utilizar los datos obtenidos según sea necesario
            System.out.println("Mensaje estado device: " + messageDeviceStatus);
            System.out.println("Estado estado device: " + statusDeviceStatus);

            DeviceWiFiStatusResponse deviceWiFiStatusResponse = responseDeviceWiFiStatus.getBody();

            // Obtener el mensaje y el estado de la respuesta
            String messageDeviceWiFiStatus = deviceWiFiStatusResponse.getMessage();
            Boolean statusDeviceWiFiStatus = deviceWiFiStatusResponse.getOn();

            // Utilizar los datos obtenidos según sea necesario
            System.out.println("Mensaje estado wifi: " + messageDeviceWiFiStatus);
            System.out.println("Estado estado wifi: " + statusDeviceWiFiStatus);

            return this.deviceMapper.toOwnDevicesResponse(device, statusDeviceStatus, statusDeviceWiFiStatus, "Se pudo establecer conexion con el arduino correctamente");

        }).toList();


    }

    @Override
    public List<ObservedDeviceResponse> getAllByObserver(int page) {
        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page, 5);

        User user = this.userService.getUserByEmail(mu.getUsername());
        List<Device> devices = new ArrayList<>();
        this.observerService.getObserversByFkUser(user, pageable).forEach(observer -> {
            devices.add(observer.getFkDevice());
        });

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "hola");
        HttpEntity<ArduinoDeviceStatusResponse> requestEntityDeviceStatus = new HttpEntity<>(headers);
        HttpEntity<DeviceWiFiStatusResponse> requestEntityDeviceWiFiStatus = new HttpEntity<>(headers);


        return devices.stream().map((device) -> {
            ResponseEntity<ArduinoDeviceStatusResponse> responseDeviceStatus = null;
            ResponseEntity<DeviceWiFiStatusResponse> responseDeviceWiFiStatus = null;
            try {
                responseDeviceStatus = restTemplate.exchange("http://192.168.0.254:80/verificar-estado-sensor", HttpMethod.GET, requestEntityDeviceStatus, new ParameterizedTypeReference<ArduinoDeviceStatusResponse>() {
                });
                responseDeviceWiFiStatus = restTemplate.exchange("http://192.168.0.254:80/verificar-wifi", HttpMethod.GET, requestEntityDeviceWiFiStatus, new ParameterizedTypeReference<DeviceWiFiStatusResponse>() {
                });
            } catch (HttpClientErrorException.NotFound enf) {
                System.out.println(enf.getMessage());
                return this.deviceMapper.toObservedDeviceResponse(device, null, null, "Arduino no encontro el recurso");
            } catch (HttpClientErrorException.Forbidden efb) {
                System.out.println(efb.getMessage());
                return this.deviceMapper.toObservedDeviceResponse(device, null, null, "Arduino no te permite el acceso");
            } catch (ResourceAccessException rae) {
                //ATRAPA ERROR 5XX
                System.out.println(rae.getMessage());
                return this.deviceMapper.toObservedDeviceResponse(device, null, null, "No se pudo establecer conexion con el arduino, probablemente no este conectado a una fuente de energia o no tenga WiFi conectado");
            }

            ArduinoDeviceStatusResponse arduinoDeviceStatusResponse = responseDeviceStatus.getBody();

            // Obtener el mensaje y el estado de la respuesta
            String messageDeviceStatus = arduinoDeviceStatusResponse.getMessage();
            Boolean statusDeviceStatus = arduinoDeviceStatusResponse.getOn();

            // Utilizar los datos obtenidos según sea necesario
            System.out.println("Mensaje estado device: " + messageDeviceStatus);
            System.out.println("Estado estado device: " + statusDeviceStatus);

            DeviceWiFiStatusResponse deviceWiFiStatusResponse = responseDeviceWiFiStatus.getBody();

            // Obtener el mensaje y el estado de la respuesta
            String messageDeviceWiFiStatus = deviceWiFiStatusResponse.getMessage();
            Boolean statusDeviceWiFiStatus = deviceWiFiStatusResponse.getOn();

            // Utilizar los datos obtenidos según sea necesario
            System.out.println("Mensaje estado wifi: " + messageDeviceWiFiStatus);
            System.out.println("Estado estado wifi: " + statusDeviceWiFiStatus);

            return this.deviceMapper.toObservedDeviceResponse(device, statusDeviceStatus, statusDeviceWiFiStatus, "Se pudo establecer conexion con el arduino correctamente");


        }).toList();

    }

    @Override
    public void changeDeviceName(String deviceCode, String newName) {

        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Device device = this.getByDeviceCode(deviceCode);

        if (device.getFkUser() != null) {
            if (device.getFkUser().getIdUser() != mu.getId()) {
                throw new GeneralException(HttpStatus.UNAUTHORIZED, "No puede cambiar el nombre del dispositivo, solo el dueño puede hacerlo");
            }
        } else {
            throw new GeneralException(HttpStatus.UNAUTHORIZED, DEVICE_WITHOUT_OWNER_MESSAGE);
        }

        device.setName(newName);
        this.deviceDao.save(device);

    }

    @Override
    @Transactional
    public void changeDevicePassword(String deviceCode, String oldPassword, String newPassword) {
        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Device device = this.getByDeviceCode(deviceCode);

        if (device.getFkUser() != null) {
            if (device.getFkUser().getIdUser() != mu.getId()) {
                throw new GeneralException(HttpStatus.UNAUTHORIZED, "No puede cambiar el nombre del dispositivo, solo el dueño puede hacerlo");
            }
        } else {
            throw new GeneralException(HttpStatus.UNAUTHORIZED, DEVICE_WITHOUT_OWNER_MESSAGE);
        }

        if (!passwordEncoder.matches(oldPassword, device.getPassword())) {
            throw new GeneralException(HttpStatus.BAD_REQUEST, "Password incorrecta");
        } else if (passwordEncoder.matches(newPassword, device.getPassword())) {
            throw new GeneralException(HttpStatus.BAD_REQUEST, "La nueva password no puede ser la misma que la anterior");
        }

        ConfirmationTokenDevicePasswordChange ctd = null;
        String token = UUID.randomUUID().toString();
        String newPasswordEncoded = this.passwordEncoder.encode(newPassword);

        try {
            ctd = this.confirmationTokenDevicePasswordChangeService.getConfirmationTokenDevicePasswordChangeByDevice(device);
            ctd.setToken(token);
            ctd.setCreatedAt(LocalDateTime.now());
            ctd.setNewPassword(newPasswordEncoded);
        } catch (GeneralException ge) {
            User user = this.userService.getUserByEmail(mu.getUsername());
            ctd = new ConfirmationTokenDevicePasswordChange(
                    token,
                    LocalDateTime.now(),
                    user,
                    device,
                    newPasswordEncoded
            );
        }

        this.confirmationTokenDevicePasswordChangeService.saveConfirmationTokenDevicePasswordChange(ctd);
        emailService.send("Cambio de contraseña al dispostivo", mu.getUsername(), buildEmailForConfirmChangeDevicePassword(token, mu.getUsername(), device.getDeviceCode(), device.getName()));

    }


    @Transactional
    @Override
    public void confirmTokenChangeDevicePassword(String token) {

        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ConfirmationTokenDevicePasswordChange confirmationToken = this.confirmationTokenDevicePasswordChangeService
                .getConfirmationTokenDevicePasswordChangeByToken(token);

        Device device = confirmationToken.getFkDevice();


        if (device.getFkUser().getIdUser() != mu.getId()) {
            throw new GeneralException(HttpStatus.UNAUTHORIZED, "EL dispositivo ya no es suyo");
        }

        device.setPassword(confirmationToken.getNewPassword());
        device.setUpdated(LocalDateTime.now());
        this.deviceDao.save(device);

        this.confirmationTokenDevicePasswordChangeService.deleteByToken(token);
    }

    @Override
    public List<InformativeMessage> getHistory(String deviceCode) {
        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.getUserByEmail(mu.getUsername());

        Device device = this.getByDeviceCode(deviceCode);

        if (device.getFkUser() != null) {
            if ((device.getFkUser().getIdUser() == mu.getId()) || (this.observerService.existsByUserAndDevice(user, device))) {
                return informativeMessageService.findByFkDevice(device);
            } else {
                throw new GeneralException(HttpStatus.UNAUTHORIZED, "No puedes ver el historial de este dispositivo");
            }
        } else {
            throw new GeneralException(HttpStatus.UNAUTHORIZED, DEVICE_WITHOUT_OWNER_MESSAGE);
        }

    }

    @Override
    public List<Observer> getObserversOnTheDevice(String deviceCode) {
        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Device device = this.getByDeviceCode(deviceCode);

        if (device.getFkUser() != null) {
            if (device.getFkUser().getIdUser() != mu.getId()) {
                throw new GeneralException(HttpStatus.UNAUTHORIZED, "No puede ver los invitados del dispositivo, solo el dueño puede hacerlo");
            }
        } else {
            throw new GeneralException(HttpStatus.UNAUTHORIZED, DEVICE_WITHOUT_OWNER_MESSAGE);
        }


        return this.observerService.findByFkDevice(device);
    }

    @Override
    @Transactional
    public void changeWiFi(String deviceCode, String ssid, String password) {
        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Device device = this.getByDeviceCode(deviceCode);

        if (device.getFkUser() != null) {
            if (device.getFkUser().getIdUser() != mu.getId()) {
                throw new GeneralException(HttpStatus.UNAUTHORIZED, "No puede ver los invitados del dispositivo, solo el dueño puede hacerlo");
            }
        } else {
            throw new GeneralException(HttpStatus.UNAUTHORIZED, DEVICE_WITHOUT_OWNER_MESSAGE);
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "hola");

        Map<String, String> body = new HashMap<>();
        body.put("ssid", ssid);
        body.put("password", password);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);


        ResponseEntity<SaveWifiResponse> httpResponse = null;

        try {
            httpResponse = restTemplate.exchange("http://192.168.0.254:80/wifi", HttpMethod.POST, requestEntity, SaveWifiResponse.class);
        } catch (HttpClientErrorException.NotFound enf) {
            System.out.println(enf.getMessage());
            throw new GeneralException(HttpStatus.NOT_FOUND, "Arduino no encontro el recurso");
        } catch (HttpClientErrorException.Forbidden efb) {
            System.out.println(efb.getMessage());
            throw new GeneralException(HttpStatus.NOT_FOUND, "Arduino no te permite el acceso");
        } catch (HttpClientErrorException.BadRequest ebr) {
            System.out.println(ebr.getMessage());
            throw new GeneralException(HttpStatus.NOT_FOUND, "No se pudo establecer conexion con el arduino, probablemente no este conectado a una fuente de energia o no tenga WiFi conectado");
        } catch (ResourceAccessException rae) {
            //ATRAPA ERROR 5XX
            System.out.println(rae.getMessage());
            throw new GeneralException(HttpStatus.NOT_FOUND, "No se pudo establecer conexion con el arduino, probablemente no este conectado a una fuente de energia o no tenga WiFi conectado");
        }

        SaveWifiResponse saveWifiResponse = httpResponse.getBody();

        // Obtener el mensaje y el estado de la respuesta
        String responseMessage = saveWifiResponse.getMessage();

        // Utilizar los datos obtenidos según sea necesario
        System.out.println("Mensaje de que se guardo el wifi: " + responseMessage);

    }

    @Override
    public DeviceStatusResponse turnOnDevice(String deviceCode) {
        return this.manageDeviceState(deviceCode,true);
    }
    @Override
    public DeviceStatusResponse turnOffDevice(String deviceCode) {
        return this.manageDeviceState(deviceCode,false);
    }


    private String buildEmailForConfirmInvitation(String link, String email) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Invitacion al dispositivo</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hola,</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">  El usuario con email:  " + email + " te ha invitado a que puedas observar el comportamiento de su dispositivo, si queres ser parte de ese dispositivo y ver los cambios de estados que este va teniendo, has clic en el siguiente link: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n </p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }


    private String buildEmailForConfirmChangeDevicePassword(String token, String email, String deviceCode, String deviceName) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Cambio de contraseña del dispositivo</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hola,</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">  has tratado de cambiar la contraseña del dispositivo con codigo: \"" + deviceCode + "\" con nombre \"" + deviceName + "\"  si has sido tu copia el siguiente token en la aplicacion: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + token + "</p></blockquote>\n </p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }


    public Device getByDeviceCode(String deviceCode) {
        return this.deviceDao.getByDeviceCode(deviceCode).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "No se encontro el dispositivo con codigo: " + deviceCode));
    }



    private DeviceStatusResponse manageDeviceState(String deviceCode, boolean on) {

        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Device device = this.getByDeviceCode(deviceCode);

        if (device.getFkUser() != null) {
            if (device.getFkUser().getIdUser() != mu.getId()) {
                throw new GeneralException(HttpStatus.UNAUTHORIZED, "No puede cambiar el estado del dispositivo, solo el dueño puede hacerlo");
            }
        } else {
            throw new GeneralException(HttpStatus.UNAUTHORIZED, DEVICE_WITHOUT_OWNER_MESSAGE);
        }


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "hola");

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(headers);


        ResponseEntity<ArduinoDeviceStatusResponse> httpResponse = null;

        String url = on ? "http://192.168.0.254:80/encender":"http://192.168.0.254:80/apagar" ;

        try {
            httpResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ArduinoDeviceStatusResponse.class);
        } catch (HttpClientErrorException.NotFound enf) {
            System.out.println(enf.getMessage());
            throw new GeneralException(HttpStatus.NOT_FOUND, "Arduino no encontro el recurso");
        } catch (HttpClientErrorException.Forbidden efb) {
            System.out.println(efb.getMessage());
            throw new GeneralException(HttpStatus.FORBIDDEN, "Arduino no te permite el acceso");
        } catch (ResourceAccessException rae) {
            //ATRAPA ERROR 5XX
            System.out.println(rae.getMessage());
            throw new GeneralException(HttpStatus.NOT_FOUND, "No se pudo establecer conexion con el arduino, probablemente no este conectado a una fuente de energia o no tenga WiFi conectado");
        }

        ArduinoDeviceStatusResponse arduinoDeviceStatusResponse = httpResponse.getBody();

        // Obtener el mensaje y el estado de la respuesta
        String responseMessage = arduinoDeviceStatusResponse.getMessage();
        Boolean isOn = arduinoDeviceStatusResponse.getOn();

        // Utilizar los datos obtenidos según sea necesario
        System.out.println("Mensaje: " + responseMessage);
        System.out.println("On: " + isOn);

        return new DeviceStatusResponse(responseMessage,isOn);

    }


}
