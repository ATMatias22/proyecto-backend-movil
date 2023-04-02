package com.sensor.app.sensor_app_movil.security.service.implementation;


import com.sensor.app.sensor_app_movil.exception.GeneralException;
import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.repository.dao.IUserDao;
import com.sensor.app.sensor_app_movil.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;
    private PasswordEncoder passwordEncoder;

    //agrego asi la dependencia porque sino me da una dependencia circular, entre la clase MainSecurity, UserDetailServiceImpl, y esta clase
    public UserServiceImpl(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email).orElseThrow(() -> new GeneralException(HttpStatus.BAD_REQUEST, "No se encontro el usuario con email: " + email));
    }

    @Override
    public void saveUser(User user) {

        userDao.getUserByEmail(user.getEmail()).ifPresent(u -> {
            throw new GeneralException(HttpStatus.BAD_REQUEST, "El email " + u.getEmail() + " ya existe");
        });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Override
    public Integer enableUser(String email) {
        return userDao.enableUser(email);
    }











}
