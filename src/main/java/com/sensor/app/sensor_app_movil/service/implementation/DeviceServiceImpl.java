package com.sensor.app.sensor_app_movil.service.implementation;

import com.sensor.app.sensor_app_movil.entity.ConfirmationTokenInvitation;
import com.sensor.app.sensor_app_movil.entity.Device;
import com.sensor.app.sensor_app_movil.entity.Observer;
import com.sensor.app.sensor_app_movil.exception.GeneralException;
import com.sensor.app.sensor_app_movil.repository.dao.IDeviceDao;
import com.sensor.app.sensor_app_movil.security.dto.MainUser;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.service.IConfirmationTokenInvitationService;
import com.sensor.app.sensor_app_movil.security.service.IEmailService;
import com.sensor.app.sensor_app_movil.security.service.IUserService;
import com.sensor.app.sensor_app_movil.service.IDeviceService;
import com.sensor.app.sensor_app_movil.service.IObserverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;



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
    public Device getByDeviceCode(String deviceCode) {
        return this.deviceDao.getByDeviceCode(deviceCode).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "No se encontro el dispositivo con codigo: " + deviceCode));
    }

    @Transactional
    @Override
    public void addObserver(String email, String deviceCode) {

        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = this.userService.getUserByEmail(email);

        Device device = this.getByDeviceCode(deviceCode);

        if(device.getFkUser() == null){
            throw new GeneralException(HttpStatus.UNAUTHORIZED, "No puede invitar a un usuario si no es el dueño del dispositivo");
        }

        if (device.getFkUser().getIdUser() != mu.getId() ) {
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
        String link = "http://localhost:8080/app_movil_sensor/api/device/confirm-invitation?token=" + token;
        emailService.send("Invitacion al dispositivo", email, buildEmailForConfirmInvitation(link, mu.getUsername()));


    }

    @Override
    public void unlinkObserver(String deviceCode) {
        MainUser mu = (MainUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userService.getUserByEmail(mu.getUsername());
        Device device  = this.getByDeviceCode(deviceCode);
        Observer observer = this.observerService.getObserverByUserAndDevice(user,device);
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

}
