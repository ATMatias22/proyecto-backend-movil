package com.sensor.app.sensor_app_movil.security.service.implementation;


import com.sensor.app.sensor_app_movil.security.dto.MainUser;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    IUserService userService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userService.getUserByEmail(email);

        return MainUser.build(user);
    }


}
