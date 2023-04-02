package com.sensor.app.sensor_app_movil.security.repository.dao.implementation;

import com.sensor.app.sensor_app_movil.security.entity.User;
import com.sensor.app.sensor_app_movil.security.repository.IUserRepository;
import com.sensor.app.sensor_app_movil.security.repository.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoImpl implements IUserDao {

    @Autowired
    private IUserRepository userRepository;


    @Override
    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Integer enableUser(String email) {
        return this.userRepository.enableUser(email);
    }


}
