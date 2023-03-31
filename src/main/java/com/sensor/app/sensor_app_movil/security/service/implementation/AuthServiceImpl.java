package com.sensor.app.sensor_app_movil.security.service.implementation;


import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.jwt.JwtProvider;
import com.sensor.app.sensor_app_movil.security.service.IAuthService;
import com.sensor.app.sensor_app_movil.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private IUserService userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String login(User user) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);

    }

    @Override
    public void register(User user) {
        this.userService.saveUser(user);
    }
}
