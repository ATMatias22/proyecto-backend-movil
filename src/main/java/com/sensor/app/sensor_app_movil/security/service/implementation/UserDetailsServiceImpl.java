package com.sensor.app.sensor_app_movil.security.service.implementation;


import com.sensor.app.sensor_app_movil.exception.GeneralException;
import com.sensor.app.sensor_app_movil.exception.constants.ExceptionMessage;
import com.sensor.app.sensor_app_movil.security.dto.MainUser;
import com.sensor.app.sensor_app_movil.security.entity.ConfirmationToken;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.exception.UnabledAccountException;
import com.sensor.app.sensor_app_movil.security.service.IConfirmationTokenService;
import com.sensor.app.sensor_app_movil.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IUserService userService;

    @Autowired
    IConfirmationTokenService confirmationTokenService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = null;

        try {
            user = userService.getUserByEmail(email);
            if(!user.getEnabled()){
                if(this.confirmationTokenService.existsTokenForFkUser(user)){
                    throw new UnabledAccountException(HttpStatus.BAD_REQUEST, ExceptionMessage.UNABLED_ACCOUNT);
                }
            }
        } catch (GeneralException ge) {
            throw new GeneralException(ge.getStatus(), ExceptionMessage.BAD_CREDENTIALS);
        }


        return MainUser.build(user);
    }

}
