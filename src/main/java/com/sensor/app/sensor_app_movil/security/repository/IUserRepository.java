package com.sensor.app.sensor_app_movil.security.repository;

import com.sensor.app.sensor_app_movil.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
